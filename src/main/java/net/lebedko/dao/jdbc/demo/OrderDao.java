package net.lebedko.dao.jdbc.demo;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.entity.demo.order.Order;

/**
 * alexandr.lebedko : 25.09.2017.
 */
public interface OrderDao {
    Order insert(Order order) throws DataAccessException;
}
