package net.lebedko.dao;

import net.lebedko.dao.paging.Page;
import net.lebedko.dao.paging.Pageable;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.invoice.InvoiceState;
import net.lebedko.entity.user.User;

public interface InvoiceDao extends GenericDao<Invoice, Long> {

    Invoice getByUserAndState(User user, InvoiceState state);

    Page<Invoice> getByUser(User user, Pageable pageable);

    Page<Invoice> getByState(InvoiceState state, Pageable pageable);
}