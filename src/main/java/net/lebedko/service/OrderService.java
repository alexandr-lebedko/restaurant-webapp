package net.lebedko.service;

import net.lebedko.dao.paging.Page;
import net.lebedko.dao.paging.Pageable;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.order.OrderState;
import net.lebedko.entity.user.User;

import java.util.Collection;

public interface OrderService {

    void createOrder(User user, OrderBucket bucket);

    Order getById(Long id);

    Order getByUserAndId(Long orderId, User user);

    Collection<Order> getByState(OrderState state);

    Page<Order> getByUser(User user, Pageable pageable);

    Page<Order> getByState(OrderState state, Pageable pageable);

    Collection<Order> getByInvoice(Invoice invoice);

    void submitModifiedOrder(Long id, User user);

    void process(Long id);

    void reject(Long id);

    void modify(Collection<OrderItem> orderItems);

    void delete(Long id, User user);
}
