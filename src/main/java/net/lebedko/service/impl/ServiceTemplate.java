package net.lebedko.service.impl;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.exception.UniqueViolationException;
import net.lebedko.dao.TransactionManager;
import net.lebedko.service.exception.EntityExistsException;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.service.exception.UnavailableServiceException;
import net.lebedko.util.VoidCallable;

import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * alexandr.lebedko : 11.05.2017.
 */

public class ServiceTemplate {
    private TransactionManager txManager;

    public ServiceTemplate(TransactionManager txManager) {
        this.txManager = Objects.requireNonNull(txManager);
    }

    public ServiceTemplate() {
        this(TransactionManager.getTxManager());
    }

    public <T> T doTxService(Callable<T> work) throws ServiceException {
        T result = null;
        try {
            result = txManager.tx(work);
        } catch (UniqueViolationException e) {
            throw new EntityExistsException(e);
        } catch (DataAccessException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    public void doTxService(VoidCallable work) throws ServiceException {
        try {
            txManager.tx(work);
        } catch (UniqueViolationException e) {
            throw new EntityExistsException(e);
        } catch (DataAccessException e) {
            throw new ServiceException(e);
        }
    }
}
