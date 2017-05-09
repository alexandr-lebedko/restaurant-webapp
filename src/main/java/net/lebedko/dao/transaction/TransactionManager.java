package net.lebedko.dao.transaction;

import net.lebedko.dao.exception.DataAccessException;

import java.util.concurrent.Callable;

/**
 * alexandr.lebedko : 08.05.2017.
 */

public interface TransactionManager {


    void begin() throws DataAccessException;

    void commit() throws DataAccessException;

    void rollback() throws DataAccessException;


    default void tx(Runnable runnable) throws DataAccessException {
        try {
            begin();
            runnable.run();
        } catch (Exception e) {
            rollback();
            throw new DataAccessException(e);
        } finally {
            commit();
        }
    }

    default <T> T tx(Callable<T> callable) throws DataAccessException {
        T result;
        try {
            begin();
            result = callable.call();
        } catch (Exception e) {
            rollback();
            throw new DataAccessException(e);
        } finally {
            commit();
        }
        return result;
    }
}
