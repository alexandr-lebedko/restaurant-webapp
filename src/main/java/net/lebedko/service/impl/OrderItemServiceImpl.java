package net.lebedko.service.impl;

import net.lebedko.dao.OrderItemDao;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.service.OrderItemService;
import net.lebedko.service.exception.ServiceException;

import java.util.Collection;

public class OrderItemServiceImpl implements OrderItemService {
    private OrderItemDao orderItemDao;
    private ServiceTemplate template;

    public OrderItemServiceImpl(OrderItemDao orderItemDao, ServiceTemplate template) {
        this.orderItemDao = orderItemDao;
        this.template = template;
    }

    @Override
    public Collection<OrderItem> getOrderItems(Invoice invoice) throws ServiceException {
        return template.doTxService(() -> orderItemDao.getByInvoice(invoice));
    }

    @Override
    public Collection<OrderItem> getOrderItems(Order order) throws ServiceException {
        return template.doTxService(() -> orderItemDao.getByOrder(order));
    }
}
