package net.lebedko.dao;

import net.lebedko.dao.paging.Page;
import net.lebedko.dao.paging.Pageable;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderState;
import net.lebedko.entity.user.User;

import java.util.Collection;

public interface OrderDao extends GenericDao<Order, Long> {

    Collection<Order> getByInvoice(Invoice invoice);

    Collection<Order> getByState(OrderState state);

    Page<Order> getByState(OrderState state, Pageable pageable);

    Page<Order> getByUser(User user, Pageable pageable);

    Order getByOrderIdAndUser(Long id, User user);
}
