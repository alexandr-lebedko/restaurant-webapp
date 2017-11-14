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

import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public Map<Item, Long> toOrderContent(Map<Long, Long> amountToItemId) {
        return template.doTxService(() ->
                amountToItemId.entrySet().stream()
                        .map(this::convertEntry)
                        .collect(toMap(Entry::getKey, Entry::getValue)));
    }

    private Entry<Item, Long> convertEntry(Entry<Long, Long> amountToId) {
        return new SimpleEntry<>(itemService.get(amountToId.getKey()), amountToId.getValue());
    }

    @Override
    public Order createOrder(User user, Map<Item, Long> content) throws ServiceException {
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

    private void insertOrderContent(Order order, Map<Item, Long> content) throws DataAccessException {
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

    @Override
    public void processOrderById(Long id) throws ServiceException {
        template.doTxService(() -> {
            ofNullable(orderDao.getById(id))
                    .filter(order -> order.getState() == State.NEW)
                    .map(order -> new Order(order.getId(), order.getInvoice(), State.PROCESSED, order.getCreatedOn()))
                    .ifPresent(orderDao::update);
        });
    }

    @Override
    public void rejectOrderById(Long id) throws ServiceException {
        template.doTxService(() -> {
            ofNullable(orderDao.getById(id))
                    .filter(order -> order.getState() == State.NEW)
                    .map(order -> new Order(order.getId(), order.getInvoice(), State.REJECTED, order.getCreatedOn()))
                    .ifPresent(orderDao::update);
        });
    }

    @Override
    public void modifyOrder(Long orderId, Map<Long, Pair<Long, Long>> itemIdAndQuantityByOrderItemIds) throws ServiceException {
        template.doTxService(() -> {

            final Order order = orderDao.getById(orderId);
            final Collection<OrderItem> orderItems = orderItemDao.getByOrder(order);

            List<OrderItem> itemsToUpdate = orderItems.stream()
                    .filter(orderItem -> itemIdAndQuantityByOrderItemIds.containsKey(orderItem.getId()))
                    .filter(orderItem -> {
                        Long quantity = itemIdAndQuantityByOrderItemIds.get(orderItem.getId()).getRight();
                        return quantity > 0 && quantity < orderItem.getQuantity();
                    })
                    .map(orderItem -> new OrderItem(
                            orderItem.getId(),
                            orderItem.getOrder(),
                            orderItem.getItem(),
                            itemIdAndQuantityByOrderItemIds.get(orderItem.getId()).getRight()))
                    .collect(Collectors.toList());

            List<OrderItem> itemsToDelete = orderItems.stream()
                    .filter(orderItem -> !itemIdAndQuantityByOrderItemIds.containsKey(orderItem.getId()))
                    .collect(Collectors.toList());


            orderItemDao.update(itemsToUpdate);
            orderItemDao.delete(itemsToDelete);
            orderDao.update(new Order(order.getId(), order.getInvoice(), State.MODIFIED, order.getCreatedOn()));
        });
    }

    @Override
    public Collection<Order> getProcessedOrder() throws ServiceException {
        return template.doTxService(()->orderDao.getByState(State.PROCESSED));
    }

    @Override
    public Collection<Order> getRejectedOrders() throws ServiceException {
        return template.doTxService(()-> orderDao.getByState(State.REJECTED));
    }

    @Override
    public Collection<Order> getModifiedOrders() throws ServiceException {
        return  template.doTxService(()->orderDao.getByState(State.MODIFIED));
    }
}
