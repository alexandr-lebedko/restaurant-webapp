package net.lebedko.service;

import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.service.exception.ServiceException;

import java.util.Collection;

public interface OrderItemService {

    Collection<OrderItem> getOrderItems(Invoice invoice) throws ServiceException;

    Collection<OrderItem> getOrderItems(Order order) throws ServiceException;

    OrderItem insert(OrderItem orderItem);
}
