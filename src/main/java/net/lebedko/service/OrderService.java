package net.lebedko.service;

import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.order.OrderState;
import net.lebedko.entity.user.User;
import net.lebedko.service.exception.ServiceException;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.Map;

/**
 * alexandr.lebedko : 02.10.2017.
 */
public interface OrderService {

    Order createOrder(User user, Collection<Pair<Long, Long>> quantityToItemId);

    Order getOrder(Long orderId, User user) throws ServiceException;

    Order getOrder(Long orderId) throws ServiceException;

    Collection<Order> getOrders(User user) throws ServiceException;

    Collection<Order> getOrders(OrderState state) throws ServiceException;

    Collection<Order> getOrders(Invoice invoice) throws ServiceException;

     void submitModifiedOrder(Long id, User user);

    void deleteModified(Long id, User user);

    void process(Long id);

    void reject(Long id);

    void modify(Long orderId, Map<Long, Pair<Long, Long>> itemIdAndQuantityByOrderItemIds) throws ServiceException;

}
