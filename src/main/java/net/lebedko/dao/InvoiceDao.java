package net.lebedko.dao;

import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.invoice.InvoiceState;
import net.lebedko.entity.user.User;

import java.util.Collection;

public interface InvoiceDao extends GenericDao<Invoice, Long> {

    Invoice getByUserAndState(User user, InvoiceState state);

    Invoice getUnpaidOrClosedByUser(User user);

    Collection<Invoice> getByState(InvoiceState state);

    Collection<Invoice> getByUser(User user);
}
