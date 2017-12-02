package net.lebedko.service.impl;

import net.lebedko.dao.OrderDao;
import net.lebedko.entity.general.Price;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.order.OrderState;
import net.lebedko.entity.user.User;
import net.lebedko.service.*;

import java.util.Collection;

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
    public Order getById(Long id) {
        return orderDao.findById(id);
    }

    @Override
    public Order getByUserAndId(Long id, User user) {
        return template.doTxService(() -> orderDao.getByOrderIdAndUser(id, user));
    }

    @Override
    public Collection<Order> getByUser(User user) {
        return template.doTxService(() -> orderDao.getByUser(user));
    }

    @Override
    public Collection<Order> getByState(OrderState state) {
        return template.doTxService(() -> orderDao.getByState(state));
    }

    @Override
    public Collection<Order> getByInvoice(Invoice invoice) {
        return template.doTxService(() -> orderDao.getByInvoice(invoice));
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
    public void modify(Collection<OrderItem> orderItems) {
        template.doTxService(() -> {
            final Order order = orderItems.stream()
                    .map(OrderItem::getOrder)
                    .filter(o -> o.getState() == OrderState.NEW)
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);

            orderItemService.deleteByOrder(order);
            orderItems.forEach(orderItemService::insert);
            orderDao.update(new Order(order.getId(), order.getInvoice(), OrderState.MODIFIED, order.getCreatedOn()));
        });
    }
}