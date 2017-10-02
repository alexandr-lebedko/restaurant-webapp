package net.lebedko.dao;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.order.Order;

/**
 * alexandr.lebedko : 02.10.2017.
 */
public interface OrderDao {
    OrderItem insert(OrderItem item) throws DataAccessException;

    Order insert(Order order) throws DataAccessException;
}
