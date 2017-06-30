package net.lebedko.dao.transaction;

import net.lebedko.dao.connection.ConnectionProvider;
import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.exception.TransactionException;
import net.lebedko.util.VoidCallable;

import java.util.concurrent.Callable;

/**
 * alexandr.lebedko : 08.05.2017.
 */

public interface TransactionManager {


    void begin() throws DataAccessException;

    void commit() throws DataAccessException;

    void rollback() throws DataAccessException;


    default <T> T tx(Callable<T> callable) throws DataAccessException {
        T result;
        try {
            begin();
            result = callable.call();
        } catch (DataAccessException e) {
            throw e;
        } catch (Exception e) {
            rollback();
            throw new TransactionException(e);
        } finally {
            commit();
        }
        return result;
    }

    default void tx(VoidCallable callable) throws DataAccessException {
        try {
            begin();
            callable.call();
        } catch (DataAccessException e) {
            throw e;
        } catch (Exception e) {
            rollback();
            throw new TransactionException(e);
        } finally {
            commit();
        }
    }

    static TransactionManager getTxManager() {
        return new TransactionManagerImpl(ConnectionProvider.getProvider());
    }
}
