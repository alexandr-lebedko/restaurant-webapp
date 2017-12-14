package net.lebedko.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Callable;

public abstract class TransactionManager {

    protected static final Logger LOG = LogManager.getLogger();

    protected abstract void begin();

    protected abstract void rollback();

    protected abstract void commit();

    public final <T> T tx(Callable<T> work) {
        try {
            begin();
            T result = work.call();
            commit();
            return result;
        } catch (RuntimeException e) {
            rollback();
            throw e;
        } catch (Exception e) {
            rollback();
            throw new RuntimeException(e);
        }
    }

    public final void tx(Runnable work) {
        try {
            begin();
            work.run();
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
