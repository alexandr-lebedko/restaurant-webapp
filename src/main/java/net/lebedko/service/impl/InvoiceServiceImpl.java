package net.lebedko.service.impl;

import net.lebedko.dao.InvoiceDao;
import net.lebedko.entity.general.Price;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.invoice.State;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.user.User;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.OrderItemService;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.NoSuchEntityException;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.service.exception.UnprocessedOrdersException;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static net.lebedko.entity.order.State.*;

/**
 * alexandr.lebedko : 02.10.2017.
 */
public class InvoiceServiceImpl implements InvoiceService {
    private InvoiceDao invoiceDao;
    private ServiceTemplate template;
    private OrderService orderService;
    private OrderItemService orderItemService;

    public InvoiceServiceImpl(InvoiceDao invoiceDao, OrderItemService orderItemService, ServiceTemplate template) {
        this.invoiceDao = invoiceDao;
        this.template = template;
        this.orderItemService = orderItemService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }


    @Override
    public Invoice getInvoice(Long id) throws ServiceException {
        return template.doTxService(() -> invoiceDao.get(id));
    }

    @Override
    public Invoice getInvoice(Long invoiceId, User user) throws ServiceException {
        return template.doTxService(() -> ofNullable(invoiceDao.get(invoiceId))
                .filter(invoice -> invoice.getUser().equals(user)))
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Collection<Invoice> getInvoices(User user) {
        return template.doTxService(()->invoiceDao.get(user));
    }

    @Override
    public Invoice getUnpaid(User user) throws ServiceException {
        return template.doTxService(() -> invoiceDao.get(user, State.UNPAID));
    }

    @Override
    public Invoice getActive(User user) throws ServiceException {
        return template.doTxService(() -> invoiceDao.get(user, State.ACTIVE));
    }

    @Override
    public void closeActiveInvoice(User user) throws ServiceException {
        template.doTxService(() -> {
            Invoice invoice = ofNullable(getActive(user))
                    .orElseThrow(NoSuchElementException::new);

            Collection<OrderItem> orderItems = orderItemService.getOrderItems(invoice);

        });
    }

    @Override
    public void closeInvoice(Long id, User user) throws ServiceException {
        template.doTxService(() -> {
            Invoice invoice = ofNullable(invoiceDao.get(id))
                    .filter(i -> i.getUser().equals(user))
                    .filter(i -> i.getState().equals(State.ACTIVE))
                    .orElseThrow(NoSuchElementException::new);

            Collection<OrderItem> orderItems = orderItemService.getOrderItems(invoice);

            orderItems.stream()
                    .map(OrderItem::getOrder)
                    .map(Order::getState)
                    .filter(state -> (state.equals(NEW)) || (state.equals(MODIFIED)))
                    .findFirst()
                    .ifPresent(state -> {
                        throw new IllegalStateException();
                    });


            Price price = orderItems.stream()
                    .filter(orderItem -> orderItem.getOrder().getState() != REJECTED)
                    .map(OrderItem::getPrice)
                    .reduce(Price::sum)
                    .orElse(new Price(0.));

            invoiceDao.update(Invoice.Builder(invoice)
                    .setState(State.CLOSED)
                    .setPrice(price)
                    .build());
        });
    }

    @Override
    public void payInvoice(Long id, User user) throws ServiceException {
        template.doTxService(() -> {
            Invoice invoice = ofNullable(invoiceDao.get(id))
                    .filter(inv -> inv.getUser().equals(user))
                    .orElseThrow(NoSuchElementException::new);

            if (invoice.getState().equals(State.UNPAID)) {
                invoiceDao.update(Invoice.Builder(invoice).setState(State.PAID).build());
            }
            throw new IllegalStateException();
        });
    }

    @Override
    public Invoice getActiveOrCreate(User user) throws ServiceException {
        return template.doTxService(() -> {
            Invoice unpaidInvoice = getActive(user);
            if (nonNull(unpaidInvoice)) {
                return unpaidInvoice;
            }
            return createInvoice(user);
        });
    }

    private Invoice createInvoice(User user) throws ServiceException {
        return template.doTxService(() -> invoiceDao.insert(new Invoice(user)));
    }

    private boolean hasUnprocessedOrders(Invoice invoice) throws ServiceException {
        return !orderService.getUnprocessed(invoice).isEmpty();
    }

    @Override
    public boolean hasUnpaidOrClosed(User user) throws ServiceException {
        return nonNull(template.doTxService(() -> invoiceDao.getUnpaidOrClosedByUser(user)));
    }

    @Override
    public Entry<Invoice, Collection<OrderItem>> getCurrentInvoiceAndContent(User user) throws ServiceException {
        return template.doTxService(() ->
                ofNullable(invoiceDao.getCurrentInvoice(user))
                        .map(invoice -> new SimpleEntry<>(invoice, orderService.getOrderItemsByInvoice(invoice)))
                        .orElse(null));
    }

    @Override
    public Collection<Invoice> getClosedInvoices() throws ServiceException {
        return template.doTxService(() -> invoiceDao.getByState(State.CLOSED));
    }

    @Override
    public Collection<Invoice> getByState(State state) throws ServiceException {
        return template.doTxService(() -> invoiceDao.getByState(state));
    }
}