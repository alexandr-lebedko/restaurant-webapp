package net.lebedko.service;

import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.user.User;
import net.lebedko.service.exception.ServiceException;

/**
 * alexandr.lebedko : 02.10.2017.
 */
public interface InvoiceService {
    Invoice getUnpaidOrCreate(User user) throws ServiceException;

    Invoice getUnpaid(User user) throws ServiceException;
}
