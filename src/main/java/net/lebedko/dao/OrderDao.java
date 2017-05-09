package net.lebedko.dao;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.Order.OrderStatus;
import net.lebedko.entity.order.OrderItem;

import java.util.List;

/**
 * alexandr.lebedko : 30.04.2017.
 */
public interface OrderDao extends GenericDao<Order> {

    List<Order> findOrdersByStatus(OrderStatus orderStatus) throws DataAccessException;

    OrderItem insertOrderItem(OrderItem orderItem) throws DataAccessException;

    void deleteOrderItem(int id) throws DataAccessException;

    void updateOrderItem(OrderItem orderItem) throws DataAccessException;

    List<OrderItem> getOrderItemsByOrder(Order order) throws DataAccessException;


}
