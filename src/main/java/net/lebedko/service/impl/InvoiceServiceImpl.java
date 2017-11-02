package net.lebedko.service.impl;

import net.lebedko.dao.InvoiceDao;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.invoice.State;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.user.User;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.NoSuchEntityException;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.service.exception.UnprocessedOrdersException;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

/**
 * alexandr.lebedko : 02.10.2017.
 */
public class InvoiceServiceImpl implements InvoiceService {
    private InvoiceDao invoiceDao;
    private ServiceTemplate template;
    private OrderService orderService;

    public InvoiceServiceImpl(InvoiceDao invoiceDao, ServiceTemplate template) {
        this.invoiceDao = invoiceDao;
        this.template = template;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
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
            Invoice invoice = ofNullable(getActive(user)).orElseThrow(() -> new NoSuchEntityException("User: " + user + " doesn't have active invoice"));

            if (hasUnprocessedOrders(invoice)) {
                throw new UnprocessedOrdersException();
            }

            invoiceDao.update(instantiateClosedInvoice(invoice));
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
        return orderService.getUnprocessed(invoice).isEmpty();
    }

    private Invoice instantiateClosedInvoice(Invoice invoice) {
        return new Invoice(invoice.getId(), invoice.getUser(), State.CLOSED, invoice.getAmount(), invoice.getCreatedOn());
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
}