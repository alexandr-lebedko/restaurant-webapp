package net.lebedko.service.demo;

import net.lebedko.entity.demo.order.Order;
import net.lebedko.service.exception.ServiceException;

/**
 * alexandr.lebedko : 25.09.2017.
 */
public interface OrderService {
    Order createOrder(Order order) throws ServiceException;
}
