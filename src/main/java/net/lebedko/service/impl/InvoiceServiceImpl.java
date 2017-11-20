package net.lebedko.service.impl;

import net.lebedko.dao.InvoiceDao;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.invoice.InvoiceState;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.order.OrderState;
import net.lebedko.entity.user.User;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.OrderItemService;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

/**
 * alexandr.lebedko : 02.10.2017.
 */
public class InvoiceServiceImpl implements InvoiceService {
    private ServiceTemplate template;
    private OrderService orderService;
    private InvoiceDao invoiceDao;

    public InvoiceServiceImpl(ServiceTemplate template, InvoiceDao invoiceDao) {
        this.template = template;
        this.invoiceDao = invoiceDao;
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
        return template.doTxService(() -> invoiceDao.get(user));
    }

    @Override
    public Invoice getUnpaid(User user) throws ServiceException {
        return template.doTxService(() -> invoiceDao.get(user, InvoiceState.UNPAID));
    }


    @Override
    public void payInvoice(Long id, User user) throws ServiceException {
        template.doTxService(() -> {
            Invoice invoice = ofNullable(invoiceDao.get(id))
                    .filter(inv -> inv.getUser().equals(user))
                    .orElseThrow(NoSuchElementException::new);

            orderService.getOrders(invoice).stream()
                    .map(Order::getState)
                    .filter(this::newOrdModified)
                    .findFirst()
                    .ifPresent(order -> {
                        throw new IllegalStateException("Invoice has new or modified orders and cannot be closed!");
                    });

            invoiceDao.update(new Invoice(invoice.getId(), invoice.getUser(), InvoiceState.PAID, invoice.getAmount(), invoice.getCreatedOn()));
        });
    }

    @Override
    public Invoice getUnpaidOrCreate(User user) throws ServiceException {
        return template.doTxService(() -> {
            Invoice unpaidInvoice = getUnpaid(user);
            if (nonNull(unpaidInvoice)) {
                return unpaidInvoice;
            }
            return createInvoice(user);
        });
    }

    private Invoice createInvoice(User user) throws ServiceException {
        return template.doTxService(() -> invoiceDao.insert(new Invoice(user)));
    }

    @Override
    public boolean hasUnpaidOrClosed(User user) throws ServiceException {
        return nonNull(template.doTxService(() -> invoiceDao.getUnpaidOrClosedByUser(user)));
    }

    @Override
    public Collection<Invoice> getByState(InvoiceState state) throws ServiceException {
        return template.doTxService(() -> invoiceDao.getByState(state));
    }

    private boolean newOrdModified(OrderState state) {
        return (state == OrderState.NEW) || (state == OrderState.MODIFIED);
    }
}