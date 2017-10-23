package net.lebedko.service.impl;

import net.lebedko.dao.OrderDao;
import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.item.Item;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.order.State;
import net.lebedko.entity.user.User;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.ItemService;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * alexandr.lebedko : 02.10.2017.
 */
public class OrderServiceImpl implements OrderService {
    private ServiceTemplate template;
    private InvoiceService invoiceService;
    private ItemService itemService;
    private OrderDao orderDao;

    public OrderServiceImpl(ServiceTemplate template, InvoiceService invoiceService, ItemService itemService, OrderDao orderDao) {
        this.template = template;
        this.invoiceService = invoiceService;
        this.itemService = itemService;
        this.orderDao = orderDao;
    }

    @Override
    public Order create(User user, Map<Long, Integer> amountToItemId) throws ServiceException {
        return template.doTxService(() -> {

            final Map<Item, Integer> orderContent = toOrderContent(amountToItemId);

            final Invoice invoice = invoiceService.getActiveOrCreate(user);
            final Order order = orderDao.insert(new Order(invoice));

            orderContent.entrySet().stream()
                    .map(e -> new OrderItem(order, e.getKey(), e.getValue()))
                    .forEach(orderDao::insert);

            return order;
        });
    }

    @Override
    public Map<Item, Integer> toOrderContent(Map<Long, Integer> amountToItemId) {
        return template.doTxService(() ->
                amountToItemId.entrySet().stream()
                        .map(this::convertEntry)
                        .collect(toMap(Entry::getKey, Entry::getValue)));
    }

    private Entry<Item, Integer> convertEntry(Entry<Long, Integer> amountToId) {
        return new SimpleEntry<Item, Integer>(itemService.get(amountToId.getKey()), amountToId.getValue());
    }

    @Override
    public Order createOrder(User user, Map<Item, Integer> content) throws ServiceException {
        return template.doTxService(() -> {
                    final Invoice invoice = invoiceService.getActiveOrCreate(user);
                    final Order order = orderDao.insert(new Order(invoice));
                    insertOrderContent(order, content);

                    return order;
                }
        );
    }

    @Override
    public Collection<Order> getUnprocessed(Invoice invoice) throws ServiceException {
        return template.doTxService(() -> orderDao.get(invoice, State.NEW));
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
