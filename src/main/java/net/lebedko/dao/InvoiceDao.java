package net.lebedko.dao;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.invoice.State;
import net.lebedko.entity.user.User;

/**
 * alexandr.lebedko : 02.10.2017.
 */
public interface InvoiceDao {

    Invoice insert(Invoice invoice) throws DataAccessException;

    Invoice get(User user, State state) throws DataAccessException;

    Invoice get(Long id) throws DataAccessException;

    Invoice update(Invoice invoice) throws DataAccessException;

    Invoice getUnpaidOrClosedByUser(User user) throws DataAccessException;
}
