package net.lebedko.service.impl;

import net.lebedko.dao.OrderItemDao;
import net.lebedko.dao.TransactionManager;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.service.OrderItemService;

import java.util.Collection;

public class OrderItemServiceImpl implements OrderItemService {
    private TransactionManager txManager;
    private OrderItemDao orderItemDao;

    public OrderItemServiceImpl(TransactionManager txManager, OrderItemDao orderItemDao) {
        this.txManager = txManager;
        this.orderItemDao = orderItemDao;
    }

    @Override
    public Collection<OrderItem> getOrderItems(Invoice invoice) {
        return txManager.tx(() -> orderItemDao.getByInvoice(invoice));
    }

    @Override
    public Collection<OrderItem> getOrderItems(Order order) {
        return txManager.tx(() -> orderItemDao.getByOrder(order));
    }

    @Override
    public OrderItem insert(OrderItem orderItem) {
        return txManager.tx(() -> orderItemDao.insert(orderItem));
    }

    @Override
    public void delete(Collection<OrderItem> orderItems) {
        txManager.tx(() -> orderItemDao.delete(orderItems));
    }

    @Override
    public void deleteByOrder(Order order) {
        txManager.tx(() -> orderItemDao.deleteByOrder(order));
    }

    @Override
    public void update(Collection<OrderItem> orderItems) {
     txManager.tx(() -> orderItemDao.update(orderItems));
    }
}