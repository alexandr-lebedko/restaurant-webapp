package net.lebedko.dao;

import net.lebedko.dao.jdbc.connection.ThreadLocalConnectionProvider;
import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.jdbc.transaction.JdbcThreadLocalTransactionManager;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.util.VoidCallable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Callable;

/**
 * alexandr.lebedko : 04.07.2017.
 */
public abstract class TransactionManager {

    protected static final Logger LOG = LogManager.getLogger();

    protected abstract void begin() throws DataAccessException;

    protected abstract void rollback() throws DataAccessException;

    protected abstract void commit() throws DataAccessException;

    public final <T> T tx(Callable<T> work) throws DataAccessException {
        try {
            begin();
            T result = work.call();
            commit();
            return  result;
        } catch (RuntimeException e) {
            rollback();
            throw e;
        } catch (Exception e) {
            rollback();
            throw new RuntimeException(e);
        }
    }

    public final void tx(VoidCallable work) throws DataAccessException {
        try {
            begin();
            work.call();
            commit();
        } catch (RuntimeException e) {
            rollback();
            throw e;
        } catch (Exception e) {
            rollback();
            throw new RuntimeException(e);
        }
    }
}
