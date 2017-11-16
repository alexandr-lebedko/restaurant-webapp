package net.lebedko.service;

import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.invoice.State;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.user.User;
import net.lebedko.service.exception.ServiceException;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

/**
 * alexandr.lebedko : 02.10.2017.
 */
public interface InvoiceService {
    Invoice getInvoice(Long id) throws ServiceException;

    Invoice getInvoice(Long invoiceId, User user) throws ServiceException;

    Collection<Invoice> getInvoices(User user);

    Invoice getActiveOrCreate(User user) throws ServiceException;

    Invoice getUnpaid(User user) throws ServiceException;

    Invoice getActive(User user) throws ServiceException;

    void closeActiveInvoice(User user) throws ServiceException;

    void closeInvoice(Long id, User user) throws ServiceException;

    void payInvoice(Long id, User user) throws ServiceException;

    boolean hasUnpaidOrClosed(User user) throws ServiceException;

    Entry<Invoice, Collection<OrderItem>> getCurrentInvoiceAndContent(User user) throws ServiceException;

    Collection<Invoice> getClosedInvoices() throws ServiceException;

    Collection<Invoice> getByState(State state) throws ServiceException;
}
