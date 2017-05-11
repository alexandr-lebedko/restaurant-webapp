package net.lebedko.service;

import net.lebedko.service.exception.ServiceException;

import java.util.concurrent.Callable;

/**
 * alexandr.lebedko : 11.05.2017.
 */
public class ServiceTemplate {

    public <T> T doService(Callable<T> work) throws ServiceException {
        T result = null;
        try {
            result = work.call();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return result;
    }
}
