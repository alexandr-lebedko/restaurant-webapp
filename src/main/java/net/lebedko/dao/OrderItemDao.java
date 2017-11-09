package net.lebedko.dao;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;

import java.util.Collection;

public interface OrderItemDao {

    OrderItem insert(OrderItem item) throws DataAccessException;

    void delete(OrderItem item) throws DataAccessException;

    void update(OrderItem item) throws DataAccessException;

    Collection<OrderItem> getByOrder(Order order) throws DataAccessException;

    Collection<OrderItem> getByInvoice(Invoice invoice) throws DataAccessException;
}
