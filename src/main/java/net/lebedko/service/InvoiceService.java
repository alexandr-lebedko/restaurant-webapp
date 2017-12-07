package net.lebedko.service;

import net.lebedko.dao.paging.Page;
import net.lebedko.dao.paging.Pageable;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.invoice.InvoiceState;
import net.lebedko.entity.user.User;

public interface InvoiceService {

    Invoice getInvoice(Long id);

    Invoice getInvoice(Long invoiceId, User user);

    Page<Invoice> getInvoices(User user, Pageable pageable);

    Page<Invoice> getByState(InvoiceState state, Pageable pageable);

    Invoice getUnpaidOrCreate(User user);

    Invoice getUnpaid(User user);

    void payInvoice(Long id, User user);

    void update(Invoice invoice);

    void delete(Invoice invoice);
}
