package net.lebedko.dao;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.State;

import java.util.Collection;
import java.util.List;

/**
 * alexandr.lebedko : 02.10.2017.
 */
public interface OrderDao {
    OrderItem insert(OrderItem item) throws DataAccessException;

    Order insert(Order order) throws DataAccessException;

    Collection<Order> get(Invoice invoice) throws DataAccessException;

    Collection<Order> get(Invoice invoice, State state) throws DataAccessException;

    Collection<Order> get(State state) throws DataAccessException;
}
