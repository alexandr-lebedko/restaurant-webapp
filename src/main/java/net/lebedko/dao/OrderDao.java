package net.lebedko.dao;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderState;
import net.lebedko.entity.user.User;

import java.util.Collection;

public interface OrderDao {

    Order insert(Order order) throws DataAccessException;

    Collection<Order> get(Invoice invoice) throws DataAccessException;

    Collection<Order> get(Invoice invoice, OrderState state) throws DataAccessException;

    Collection<Order> getByState(OrderState state) throws DataAccessException;

    Collection<Order> getByUser(User user) throws DataAccessException;

    Order getByOrderIdAndUser(Long id, User user) throws DataAccessException;

    Order getById(Long id) throws DataAccessException;

    void update(Order order) throws DataAccessException;

    void delete(Order order) throws DataAccessException;
}
