package net.lebedko.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public abstract class TransactionManager {

    protected static final Logger LOG = LogManager.getLogger();

    protected abstract void begin();

    protected abstract void rollback();

    protected abstract void commit();

    public final <T> T tx(Supplier<T> work) {
        try {
            begin();
            T result = work.get();
            commit();
            return result;
        } catch (RuntimeException e) {
            rollback();
            throw e;
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
        }
    }
}
