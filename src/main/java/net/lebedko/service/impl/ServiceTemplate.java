package net.lebedko.service.impl;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.exception.UniqueViolationException;
import net.lebedko.dao.TransactionManager;
import net.lebedko.service.exception.ServiceException;

import java.util.Objects;
import java.util.concurrent.Callable;

class ServiceTemplate {
    private TransactionManager txManager;

    ServiceTemplate(TransactionManager txManager) {
        this.txManager = Objects.requireNonNull(txManager);
    }

    <T> T doTxService(Callable<T> work) {
        try {
            return txManager.tx(work);
        } catch (UniqueViolationException e) {
            throw new IllegalArgumentException(e);
        } catch (DataAccessException e) {
            throw new ServiceException(e);
        }
    }

    void doTxService(Runnable work) {
        try {
            txManager.tx(work);
        } catch (UniqueViolationException e) {
            throw new IllegalArgumentException(e);
        } catch (DataAccessException e) {
            throw new ServiceException(e);
        }
    }
}
