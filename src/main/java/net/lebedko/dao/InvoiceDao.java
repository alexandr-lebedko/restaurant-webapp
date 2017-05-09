package net.lebedko.dao;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.invoice.InvoiceView;
import net.lebedko.entity.user.User;

import java.util.List;
import java.util.Optional;

/**
 * alexandr.lebedko : 21.04.2017.
 */

public interface InvoiceDao extends GenericDao<Invoice> {

    List<InvoiceView> getUnpaidInvoices() throws DataAccessException;

    List<InvoiceView> getUnpaidInvoicesByUser(User client) throws DataAccessException;


}
