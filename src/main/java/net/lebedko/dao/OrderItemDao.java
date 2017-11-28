package net.lebedko.dao;

import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;

import java.util.Collection;

public interface OrderItemDao extends GenericDao<OrderItem, Long> {

    void update(Collection<OrderItem> items);

    void delete(Collection<OrderItem> items);

    Collection<OrderItem> getByOrder(Order order);

    Collection<OrderItem> getByInvoice(Invoice invoice);
}
