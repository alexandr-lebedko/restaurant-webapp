package net.lebedko.service;

import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.invoice.InvoiceState;
import net.lebedko.entity.user.User;

import java.util.Collection;

public interface InvoiceService {

    Invoice getInvoice(Long id);

    Invoice getInvoice(Long invoiceId, User user);

    Collection<Invoice> getInvoices(User user);

    Invoice getUnpaidOrCreate(User user);

    Invoice getUnpaid(User user);

    void payInvoice(Long id, User user);

    Collection<Invoice> getByState(InvoiceState state);

    void update(Invoice invoice);

    void delete(Invoice invoice);
}
