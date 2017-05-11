package net.lebedko.dao.transaction;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.exception.TransactionException;

import java.util.concurrent.Callable;

/**
 * alexandr.lebedko : 08.05.2017.
 */

public interface TransactionManager {


    void begin() throws TransactionException;

    void commit() throws TransactionException;

    void rollback() throws TransactionException;


    default void tx(Runnable runnable) throws TransactionException{
        try {
            begin();
            runnable.run();
        } catch (Exception e) {
            rollback();
            throw new TransactionException(e);
        } finally {
            commit();
        }
    }

    default <T> T tx(Callable<T> callable) throws TransactionException{
        T result;
        try {
            begin();
            result = callable.call();
        } catch (Exception e) {
            rollback();
            throw new TransactionException(e);
        } finally {
            commit();
        }
        return result;
    }
}
