package net.lebedko.service.impl;

import net.lebedko.dao.OrderDao;
import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.item.Item;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.user.User;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * alexandr.lebedko : 02.10.2017.
 */
public class OrderServiceImpl implements OrderService {
    private ServiceTemplate template;
    private InvoiceService invoiceService;
    private OrderDao orderDao;

    public OrderServiceImpl(ServiceTemplate template, InvoiceService invoiceService, OrderDao orderDao) {
        this.template = template;
        this.invoiceService = invoiceService;
        this.orderDao = orderDao;
    }

    @Override
    public Order createOrder(User user, Map<Item, Integer> content) throws ServiceException {
        return template.doTxService(() -> {
                    final Invoice invoice = invoiceService.getUnpaidOrCreate(user);
                    final Order order = orderDao.insert(new Order(invoice));
                    insertOrderContent(order, content);

                    return order;
                }
        );
    }

    private void insertOrderContent(Order order, Map<Item, Integer> content) throws DataAccessException {
        Collection<OrderItem> orderItems = content.entrySet()
                .stream()
                .map(e -> new OrderItem(order, e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        for (OrderItem orderItem : orderItems) {
            orderDao.insert(orderItem);
        }
    }

}
