package net.lebedko.service.impl;

import net.lebedko.dao.OrderDao;
import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.order.OrderState;
import net.lebedko.entity.user.User;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.ItemService;
import net.lebedko.service.OrderItemService;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static net.lebedko.entity.order.OrderState.MODIFIED;

public class OrderServiceImpl implements OrderService {
    private ServiceTemplate template;
    private InvoiceService invoiceService;
    private OrderItemService orderItemService;
    private ItemService itemService;

    private OrderDao orderDao;

    public OrderServiceImpl(
            ServiceTemplate template,
            InvoiceService invoiceService,
            OrderItemService orderItemService,
            ItemService itemService,
            OrderDao orderDao) {
        this.template = template;
        this.invoiceService = invoiceService;
        this.orderItemService = orderItemService;
        this.itemService = itemService;
        this.orderDao = orderDao;
    }

    @Override
    public Order getOrder(Long id, User user) throws ServiceException {
        return template.doTxService(() -> orderDao.getByOrderIdAndUser(id, user));
    }

    @Override
    public Order createOrder(User user, Collection<Pair<Long, Long>> quantityToItemId) throws ServiceException {
        return template.doTxService(() -> {

                    final Invoice invoice = invoiceService.getUnpaidOrCreate(user);
                    final Order order = orderDao.insert(new Order(invoice));

                    insertOrderContent(order, quantityToItemId);

                    return order;
                }
        );
    }

    private void insertOrderContent(Order order, Collection<Pair<Long, Long>> quantityToItemId) throws DataAccessException {
        quantityToItemId.stream()
                .map(e -> new OrderItem(order, itemService.get(e.getLeft()), e.getRight()))
                .forEach(orderItemService::insert);
    }

    @Override
    public Collection<Order> getOrders(User user) {
        return template.doTxService(() -> orderDao.getByUser(user));
    }

    @Override
    public void process(Long orderId) throws ServiceException {
        template.doTxService(() -> {
            ofNullable(orderDao.getById(orderId))
                    .filter(order -> order.getState() == OrderState.NEW)
                    .map(order -> new Order(order.getId(), order.getInvoice(), OrderState.PROCESSED, order.getCreatedOn()))
                    .ifPresent(orderDao::update);
        });
    }

    @Override
    public void reject(Long id) throws ServiceException {
        template.doTxService(() -> {
            ofNullable(orderDao.getById(id))
                    .filter(order -> order.getState() == OrderState.NEW)
                    .map(order -> new Order(order.getId(), order.getInvoice(), OrderState.REJECTED, order.getCreatedOn()))
                    .ifPresent(orderDao::update);
        });
    }

    @Override
    public void modify(Long orderId, Map<Long, Pair<Long, Long>> itemIdAndQuantityByOrderItemIds) throws ServiceException {
        template.doTxService(() -> {

            final Order order = orderDao.getById(orderId);
            final Collection<OrderItem> orderItems = orderItemService.getOrderItems(order);

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


//            orderItemDao.update(itemsToUpdate);
//            orderItemDao.delete(itemsToDelete);
            orderDao.update(Order.builder(order)
                    .setState(MODIFIED)
                    .build());
        });
    }

    @Override
    public Collection<Order> getOrders(OrderState state) throws ServiceException {
        return template.doTxService(() -> orderDao.getByState(state));
    }

    @Override
    public Collection<Order> getOrders(Invoice invoice) throws ServiceException {
        return template.doTxService(() -> orderDao.get(invoice));
    }

    @Override
    public void submitModifiedOrder(Long id, User user) {
        template.doTxService(() -> {
            Order order = ofNullable(orderDao.getByOrderIdAndUser(id, user))
                    .orElseThrow(NoSuchElementException::new);

            if (order.getState() != OrderState.MODIFIED) {
                throw new IllegalStateException("Cannot submit order which not in 'MODIFIED' state");
            }

            Order newOrder = Order.builder(order)
                    .setState(OrderState.NEW)
                    .build();

            orderDao.update(newOrder);
        });
    }

    @Override
    public Order getOrder(Long orderId) throws ServiceException {
        return orderDao.getById(orderId);
    }

    @Override
    public void deleteModified(Long id, User user) {

    }
}
