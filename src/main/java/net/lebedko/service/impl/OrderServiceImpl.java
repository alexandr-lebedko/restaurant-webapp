package net.lebedko.service.impl;

import net.lebedko.dao.OrderDao;
import net.lebedko.entity.general.Price;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.order.OrderState;
import net.lebedko.entity.user.User;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.ItemService;
import net.lebedko.service.OrderItemService;
import net.lebedko.service.OrderService;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

public class OrderServiceImpl implements OrderService {
    private ServiceTemplate template;
    private InvoiceService invoiceService;
    private OrderItemService orderItemService;
    private ItemService itemService;

    private OrderDao orderDao;

    OrderServiceImpl(
            ServiceTemplate template,
            InvoiceService invoiceService,
            OrderItemService orderItemService,
            ItemService itemService,
            OrderDao orderDao) {
        this.template = template;
        this.invoiceService = invoiceService;
        this.orderItemService = orderItemService;
        this.itemService = itemService;
        this.orderDao = orderDao;
    }

    @Override
    public Order getByUserAndId(Long id, User user) {
        return template.doTxService(() -> orderDao.getByOrderIdAndUser(id, user));
    }

    @Override
    public Order createOrder(User user, Collection<Pair<Long, Long>> quantityToItemId) {
        return template.doTxService(() -> {
                    final Invoice invoice = invoiceService.getUnpaidOrCreate(user);
                    final Order order = orderDao.insert(new Order(invoice));
                    insertOrderContent(order, quantityToItemId);

                    return order;
                }
        );
    }

    private void insertOrderContent(Order order, Collection<Pair<Long, Long>> quantityToItemId) {
        quantityToItemId.stream()
                .map(e -> new OrderItem(order, itemService.get(e.getLeft()), e.getRight()))
                .forEach(orderItemService::insert);
    }

    @Override
    public Collection<Order> getByUser(User user) {
        return template.doTxService(() -> orderDao.getByUser(user));
    }

    @Override
    public void process(Long orderId) {
        template.doTxService(() -> {
            final Order order = of(orderDao.findById(orderId))
                    .filter(o -> o.getState().equals(OrderState.NEW))
                    .map(o -> new Order(o.getId(), o.getInvoice(), OrderState.PROCESSED, o.getCreatedOn()))
                    .orElseThrow(IllegalArgumentException::new);
            final Price sum = orderItemService.getOrderItems(order).stream()
                    .map(OrderItem::getPrice)
                    .reduce(new Price(0d), Price::sum);
            final Invoice invoice = new Invoice(order.getInvoice().getId(),
                    order.getInvoice().getUser(),
                    order.getInvoice().getState(),
                    sum,
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
    public void modify(Long orderId, Map<Long, Pair<Long, Long>> itemIdAndQuantityByOrderItemIds) {
        //TODO: REFACTOR METHOD!!!
        template.doTxService(() -> {

            final Order order = orderDao.findById(orderId);
            final Collection<OrderItem> orderItems = orderItemService.getOrderItems(order);

            List<OrderItem> itemsToUpdate = orderItems.stream()
                    .filter(orderItem -> itemIdAndQuantityByOrderItemIds.containsKey(orderItem.getId()))
                    .filter(orderItem -> {
                        Long quantity = itemIdAndQuantityByOrderItemIds.get(orderItem.getId()).getRight();
                        return quantity > 0 && quantity < orderItem.getQuantity();
                    })
                    .map(orderItem -> new OrderItem(
                            orderItem.getId(),
                            orderItem.getOrder(),
                            orderItem.getItem(),
                            itemIdAndQuantityByOrderItemIds.get(orderItem.getId()).getRight()))
                    .collect(Collectors.toList());

            List<OrderItem> itemsToDelete = orderItems.stream()
                    .filter(orderItem -> !itemIdAndQuantityByOrderItemIds.containsKey(orderItem.getId()))
                    .collect(Collectors.toList());


//            orderItemDao.update(itemsToUpdate);
//            orderItemDao.delete(itemsToDelete);
//            orderDao.update(Order.builder(order)
//                    .setState(MODIFIED)
//                    .build());
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
    public void deleteModified(Long id, User user) {
        template.doTxService(() -> {
            ofNullable(orderDao.getByOrderIdAndUser(id, user))
                    .map(Order::getId)
                    .ifPresent(orderDao::delete);
        });
    }
}
