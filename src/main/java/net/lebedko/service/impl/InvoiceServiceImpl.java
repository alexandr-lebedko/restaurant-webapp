package net.lebedko.service.impl;

import net.lebedko.dao.InvoiceDao;
import net.lebedko.dao.TransactionManager;
import net.lebedko.dao.paging.Page;
import net.lebedko.dao.paging.Pageable;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.invoice.InvoiceState;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderState;
import net.lebedko.entity.user.User;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.OrderService;

import java.util.NoSuchElementException;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

public class InvoiceServiceImpl implements InvoiceService {
    private TransactionManager txManager;
    private OrderService orderService;
    private InvoiceDao invoiceDao;

    public InvoiceServiceImpl(TransactionManager txManager, InvoiceDao invoiceDao) {
        this.txManager = txManager;
        this.invoiceDao = invoiceDao;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public Invoice getInvoice(Long id) {
        return txManager.tx(() -> invoiceDao.findById(id));
    }

    @Override
    public Invoice getInvoice(Long invoiceId, User user) {
        return txManager.tx(() -> ofNullable(invoiceDao.findById(invoiceId))
                .filter(invoice -> invoice.getUser().equals(user)))
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Page<Invoice> getInvoices(User user, Pageable pageable) {
        return txManager.tx(() -> invoiceDao.getByUser(user, pageable));
    }

    @Override
    public Page<Invoice> getByState(InvoiceState state, Pageable pageable) {
        return txManager.tx(() -> invoiceDao.getByState(state, pageable));
    }

    @Override
    public Invoice getUnpaid(User user) {
        return txManager.tx(() -> invoiceDao.getByUserAndState(user, InvoiceState.UNPAID));
    }

    @Override
    public void payInvoice(Long id, User user) {
        txManager.tx(() -> {
            Invoice invoice = ofNullable(invoiceDao.findById(id))
                    .filter(inv -> inv.getUser().equals(user))
                    .orElseThrow(NoSuchElementException::new);

            orderService.getByInvoice(invoice).stream()
                    .map(Order::getState)
                    .filter(this::newOrdModified)
                    .findFirst()
                    .ifPresent(order -> {
                        throw new IllegalStateException("Invoice has new or modified orders and cannot be paid!");
                    });

            invoiceDao.update(new Invoice(invoice.getId(), invoice.getUser(), InvoiceState.PAID, invoice.getAmount(), invoice.getCreatedOn()));
        });
    }

    @Override
    public Invoice getUnpaidOrCreate(User user) {
        return txManager.tx(() -> {
            Invoice unpaidInvoice = getUnpaid(user);
            return nonNull(unpaidInvoice) ? unpaidInvoice : createInvoice(user);
        });
    }

    private Invoice createInvoice(User user) {
        return txManager.tx(() -> invoiceDao.insert(new Invoice(user)));
    }

    @Override
    public void update(Invoice invoice) {
        txManager.tx(() -> invoiceDao.update(invoice));
    }

    @Override
    public void delete(Invoice invoice) {
        txManager.tx(() -> invoiceDao.delete(invoice.getId()));
    }

    private boolean newOrdModified(OrderState state) {
        return (state == OrderState.NEW) || (state == OrderState.MODIFIED);
    }
}