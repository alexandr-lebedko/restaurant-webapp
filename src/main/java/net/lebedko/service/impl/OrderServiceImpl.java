package net.lebedko.service.impl;

import net.lebedko.dao.OrderDao;
import net.lebedko.entity.general.Price;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.order.OrderState;
import net.lebedko.entity.user.User;
import net.lebedko.service.*;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

public class OrderServiceImpl implements OrderService {
    private ServiceTemplate template;
    private InvoiceService invoiceService;
    private OrderItemService orderItemService;

    private OrderDao orderDao;

    OrderServiceImpl(ServiceTemplate template, InvoiceService invoiceService, OrderItemService orderItemService, OrderDao orderDao) {
        this.template = template;
        this.invoiceService = invoiceService;
        this.orderItemService = orderItemService;
        this.orderDao = orderDao;
    }

    @Override
    public Order getByUserAndId(Long id, User user) {
        return template.doTxService(() -> orderDao.getByOrderIdAndUser(id, user));
    }

    public void createOrder(User user, OrderBucket bucket) {
        template.doTxService(() -> {
            final Invoice invoice = invoiceService.getUnpaidOrCreate(user);
            final Order order = orderDao.insert(new Order(invoice));

            bucket.getContent().entrySet().stream()
                    .map(e -> new OrderItem(order, e.getKey(), e.getValue()))
                    .forEach(orderItemService::insert);
        });
    }

    @Override
    public Collection<Order> getByUser(User user) {
        return template.doTxService(() -> orderDao.getByUser(user));
    }

    @Override
    public void process(Long orderId) {
        template.doTxService(() -> {
            final Order order = ofNullable(orderDao.findById(orderId))
                    .filter(o -> o.getState().equals(OrderState.NEW))
                    .map(o -> new Order(o.getId(), o.getInvoice(), OrderState.PROCESSED, o.getCreatedOn()))
                    .orElseThrow(IllegalArgumentException::new);

            final Price orderSum = orderItemService.getOrderItems(order).stream()
                    .map(OrderItem::getPrice)
                    .reduce(new Price(0d), Price::sum);

            final Invoice invoice = new Invoice(
                    order.getInvoice().getId(),
                    order.getInvoice().getUser(),
                    order.getInvoice().getState(),
                    Price.sum(order.getInvoice().getAmount(), orderSum),
                    order.getInvoice().getCreatedOn());

            invoiceService.update(invoice);
            orderDao.update(order);
        });
    }

    @Override
    public void reject(Long id) {
        template.doTxService(() -> {
            ofNullable(orderDao.findById(id))
                    .filter(order -> order.getState() == OrderState.NEW)
                    .map(order -> new Order(order.getId(), order.getInvoice(), OrderState.REJECTED, order.getCreatedOn()))
                    .ifPresent(orderDao::update);
        });
    }

    @Override
    public void delete(Long id, User user) {
        template.doTxService(() -> {
            final Order order = of(orderDao.getByOrderIdAndUser(id, user))
                    .filter(o -> (o.getState() == OrderState.MODIFIED) || (o.getState() == OrderState.NEW))
                    .orElseThrow(IllegalStateException::new);

            orderDao.delete(order.getId());
            if (orderDao.getByInvoice(order.getInvoice()).isEmpty()) {
                invoiceService.delete(order.getInvoice());
            }
        });
    }

    @Override
    public Collection<Order> getByState(OrderState state) {
        return template.doTxService(() -> orderDao.getByState(state));
    }

    @Override
    public Collection<Order> getByInvoice(Invoice invoice) {
        return template.doTxService(() -> orderDao.getByInvoice(invoice));
    }

    @Override
    public void submitModifiedOrder(Long id, User user) {
        template.doTxService(() -> {
            Order order = orderDao.getByOrderIdAndUser(id, user);
            if (order.getState() != OrderState.MODIFIED) {
                throw new IllegalStateException("Cannot submit order which not in 'MODIFIED' state");
            }
            orderDao.update(new Order(order.getId(), order.getInvoice(), OrderState.NEW, order.getCreatedOn()));
        });
    }

    @Override
    public Order getById(Long id) {
        return orderDao.findById(id);
    }

    @Override
    public void modify(Pair<Order, Collection<OrderItem>> itemsToOrder) {
        final Order order = itemsToOrder.getKey();
        if (order.getState() != OrderState.NEW) {
            throw new IllegalArgumentException("Cannot modify not 'NEW' order. Order state is: " + order.getState());
        }

        final Map<Long, OrderItem> modifiedOrderItemsToId = itemsToOrder.getValue().stream()
                .collect(Collectors.toMap(OrderItem::getId, Function.identity()));

        final Map<Long, OrderItem> oldOrderItemsToId = orderItemService.getOrderItems(order).stream()
                .collect(Collectors.toMap(OrderItem::getId, Function.identity()));

        final Collection<OrderItem> orderItemsToDelete = getOrderItemsToDelete(modifiedOrderItemsToId, oldOrderItemsToId);
        final Collection<OrderItem> orderItemsToUpdate = getOrderItemsToUpdate(modifiedOrderItemsToId, oldOrderItemsToId);

        template.doTxService(() -> {
            orderItemService.delete(orderItemsToDelete);
            orderItemService.update(orderItemsToUpdate);
            orderDao.update(new Order(order.getId(), order.getInvoice(), OrderState.MODIFIED, order.getCreatedOn()));
        });

    }

    private Collection<OrderItem> getOrderItemsToDelete(Map<Long, OrderItem> modifiedOrderItemsToId, Map<Long, OrderItem> oldOrderItemsToId) {
        Map<Long, OrderItem> orderItemsToId = new HashMap<>(oldOrderItemsToId);
        orderItemsToId.keySet().removeAll(modifiedOrderItemsToId.keySet());
        return orderItemsToId.values();
    }

    private Collection<OrderItem> getOrderItemsToUpdate(Map<Long, OrderItem> modifiedOrderItemsToId, Map<Long, OrderItem> oldOrderItemsToId) {
        return modifiedOrderItemsToId.entrySet().stream()
                .filter(oi -> oi.getValue().getQuantity() < oldOrderItemsToId.get(oi.getKey()).getQuantity())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}
