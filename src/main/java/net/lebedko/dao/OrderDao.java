package net.lebedko.dao;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.entity.order.Order;

/**
 * alexandr.lebedko : 25.09.2017.
 */
public interface OrderDao {
    Order insert(Order order) throws DataAccessException;
}
