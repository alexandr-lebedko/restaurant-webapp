package net.lebedko.service;

import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.item.Item;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.order.State;
import net.lebedko.entity.user.User;
import net.lebedko.service.exception.ServiceException;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.Map;

/**
 * alexandr.lebedko : 02.10.2017.
 */
public interface OrderService {
    Order createOrder(User user, Map<Item, Long> content) throws ServiceException;

    Collection<Order> getUnprocessed(Invoice invoice) throws ServiceException;

    Map<Item, Long> toOrderContent(Map<Long, Long> amountById) throws ServiceException;

    Collection<Order> getOrders(User user) throws ServiceException;

    Collection<OrderItem> getByOrderIdAndUser(Long id, User user) throws ServiceException;

    Collection<OrderItem> getOrderItemsByInvoice(Invoice invoice) throws ServiceException;

    Pair<Order, Collection<OrderItem>> getOrderAndOrderItemsByOrderId(Long id) throws ServiceException;

    Order getOrder(Long orderId, User user) throws ServiceException;

    Collection<Order> getOrders(State state) throws ServiceException;

    Collection<Order> getOrders(Invoice invoice) throws ServiceException;

    Collection<OrderItem> getOrderItems(Order order) throws ServiceException;

    Order submitModifiedOrder(Long id, User user) throws ServiceException;

    Order rejectOrder(Long id, User user) throws ServiceException;

    Pair<Order, Collection<OrderItem>> deleteOrder(Long id, User user) throws ServiceException;

    void processOrder(Long orderId) throws ServiceException;

    void rejectOrder(Long orderId) throws ServiceException;

    void modifyOrder(Long orderId, Map<Long, Pair<Long, Long>> itemIdAndQuantityByOrderItemIds) throws ServiceException;

}
