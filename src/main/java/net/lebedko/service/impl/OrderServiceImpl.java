package net.lebedko.service.impl;

import net.lebedko.dao.OrderDao;
import net.lebedko.entity.order.Order;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.service.impl.ServiceTemplate;

/**
 * alexandr.lebedko : 25.09.2017.
 */
public class OrderServiceImpl implements OrderService {
    private ServiceTemplate template;
    private OrderDao orderDao;

    public OrderServiceImpl(ServiceTemplate template, OrderDao orderDao) {
        this.template = template;
        this.orderDao = orderDao;
    }

    @Override
    public Order createOrder(Order order) throws ServiceException {
        return template.doTxService(() -> orderDao.insert(order));
    }
}
