package net.lebedko.service.impl;

import net.lebedko.dao.InvoiceDao;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.invoice.State;
import net.lebedko.entity.user.User;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.exception.ServiceException;

import static java.util.Objects.nonNull;

/**
 * alexandr.lebedko : 02.10.2017.
 */
public class InvoiceServiceImpl implements InvoiceService {
    private InvoiceDao invoiceDao;
    private ServiceTemplate template;


    public InvoiceServiceImpl(InvoiceDao dao, ServiceTemplate template) {
        this.invoiceDao = dao;
        this.template = template;
    }

    private Invoice createInvoice(User user) throws ServiceException {
        return template.doTxService(() -> invoiceDao.insert(new Invoice(user)));
    }

    @Override
    public Invoice getUnpaid(User user) throws ServiceException {
        return template.doTxService(() -> invoiceDao.get(user, State.UNPAID));
    }

    @Override
    public Invoice getUnpaidOrCreate(User user) throws ServiceException {
        return template.doTxService(() -> {
            Invoice unpaidInvoice = getUnpaid(user);
            if (nonNull(unpaidInvoice)) {
                return unpaidInvoice;
            }
            return createInvoice(user);
        });
    }
}