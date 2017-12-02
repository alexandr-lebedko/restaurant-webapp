package net.lebedko.service;

import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;

import java.util.Collection;

public interface OrderItemService {

    OrderItem insert(OrderItem orderItem);

    Collection<OrderItem> getOrderItems(Invoice invoice);

    Collection<OrderItem> getOrderItems(Order order);

    void delete(Collection<OrderItem> orderItems);

    void deleteByOrder(Order order);

    void update(Collection<OrderItem> orderItems);
}
