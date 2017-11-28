package net.lebedko.service.impl;

import net.lebedko.dao.OrderItemDao;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.service.OrderItemService;

import java.util.Collection;

public class OrderItemServiceImpl implements OrderItemService {
    private ServiceTemplate template;
    private OrderItemDao orderItemDao;

    public OrderItemServiceImpl(ServiceTemplate template, OrderItemDao orderItemDao) {
        this.template = template;
        this.orderItemDao = orderItemDao;
    }

    @Override
    public Collection<OrderItem> getOrderItems(Invoice invoice) {
        return template.doTxService(() -> orderItemDao.getByInvoice(invoice));
    }

    @Override
    public Collection<OrderItem> getOrderItems(Order order) {
        return template.doTxService(() -> orderItemDao.getByOrder(order));
    }

    @Override
    public OrderItem insert(OrderItem orderItem) {
        return template.doTxService(() -> orderItemDao.insert(orderItem));
    }
}
