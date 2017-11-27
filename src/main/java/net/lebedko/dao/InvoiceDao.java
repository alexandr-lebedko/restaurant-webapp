package net.lebedko.dao;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.invoice.InvoiceState;
import net.lebedko.entity.user.User;

import java.util.Collection;

/**
 * alexandr.lebedko : 02.10.2017.
 */
public interface InvoiceDao {

    Invoice insert(Invoice invoice) throws DataAccessException;

    Invoice get(User user, InvoiceState state) throws DataAccessException;

    Invoice get(Long id) throws DataAccessException;

    Invoice update(Invoice invoice) throws DataAccessException;

    Invoice getUnpaidOrClosedByUser(User user) throws DataAccessException;

    Invoice getCurrentInvoice(User user) throws DataAccessException;

    Collection<Invoice> getByState(InvoiceState state) throws DataAccessException;

    Collection<Invoice> get(User user);

    void delete(Invoice invoice);
}
