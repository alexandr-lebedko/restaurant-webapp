package net.lebedko.service;

import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.item.Item;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.user.User;
import net.lebedko.service.exception.ServiceException;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * alexandr.lebedko : 02.10.2017.
 */
public interface OrderService {
    Order createOrder(User user, Map<Item, Integer> content) throws ServiceException;

    Collection<Order> getUnprocessed(Invoice invoice) throws ServiceException;

    Map<Item, Integer> toOrderContent(Map<Long, Integer> amountById) throws ServiceException;

    Collection<Order> getOrdersByUser(User user) throws ServiceException;

    Collection<OrderItem> getByOrderIdAndUser(Long id, User user) throws ServiceException;

    Collection<OrderItem> getOrderItemsByInvoice(Invoice invoice) throws ServiceException;

    Collection<Order> getUnprocessedOrders() throws ServiceException;

    Pair<Order, Collection<OrderItem>> getOrderAndOrderItemsByOrderId(Long id) throws ServiceException;
}
