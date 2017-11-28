package net.lebedko.dao;

import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderState;
import net.lebedko.entity.user.User;

import java.util.Collection;

public interface OrderDao extends GenericDao<Order, Long> {


    Collection<Order> getByInvoice(Invoice invoice);

    Collection<Order> getByInvoiceAndState(Invoice invoice, OrderState state);

    Collection<Order> getByState(OrderState state);

    Collection<Order> getByUser(User user);

    Order getByOrderIdAndUser(Long id, User user);

}
