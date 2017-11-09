package net.lebedko.service.impl;

import net.lebedko.dao.OrderDao;
import net.lebedko.dao.OrderItemDao;
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
import net.lebedko.service.exception.ClosedInvoiceException;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

/**
 * alexandr.lebedko : 02.10.2017.
 */
public class OrderServiceImpl implements OrderService {
    private static final Logger LOG = LogManager.getLogger();
    private ServiceTemplate template;
    private InvoiceService invoiceService;
    private ItemService itemService;
    private OrderDao orderDao;
    private OrderItemDao orderItemDao;

    public OrderServiceImpl(ServiceTemplate template, InvoiceService invoiceService, ItemService itemService, OrderDao orderDao, OrderItemDao orderItemDao) {
        this.template = template;
        this.invoiceService = invoiceService;
        this.itemService = itemService;
        this.orderDao = orderDao;
        this.orderItemDao = orderItemDao;
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
                    if (invoiceService.hasUnpaidOrClosed(user)) {
                        LOG.error("Cannot create new order. User: " + user + " has unpaid or closed invoice!");
                        throw new ClosedInvoiceException();
                    }

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
        content.entrySet()
                .stream()
                .map(e -> new OrderItem(order, e.getKey(), e.getValue()))
                .forEach(orderDao::insert);
    }

    @Override
    public Collection<Order> getOrdersByUser(User user) {
        return template.doTxService(() -> orderDao.getByUser(user));
    }

    @Override
    public Collection<OrderItem> getByOrderIdAndUser(Long id, User user) throws ServiceException {
        return template.doTxService(() -> {
            Optional<Order> order = ofNullable(orderDao.getByOrderIdAndUser(id, user));
            if (order.isPresent()) {
                return orderDao.getByOrder(order.get());
            }
            return Collections.emptyList();
        });
    }

    @Override
    public Collection<OrderItem> getOrderItemsByInvoice(Invoice invoice) throws ServiceException {
        return template.doTxService(() -> orderDao.getOrderItemsByInvoice(invoice));
    }

    @Override
    public Collection<Order> getUnprocessedOrders() throws ServiceException {
        return template.doTxService(() -> orderDao.getByState(State.NEW));
    }

    @Override
    public Pair<Order, Collection<OrderItem>> getOrderAndOrderItemsByOrderId(Long id) throws ServiceException {
        return template.doTxService(() ->
                ofNullable(orderDao.getById(id))
                        .map((Order order) -> Pair.of(order, orderItemDao.getByOrder(order)))
                        .orElse(null)
        );
    }
}
