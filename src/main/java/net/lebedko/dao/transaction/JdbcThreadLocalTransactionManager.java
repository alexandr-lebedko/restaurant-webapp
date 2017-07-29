package net.lebedko.dao.transaction;

import net.lebedko.dao.connection.ThreadLocalConnectionProvider;
import net.lebedko.dao.exception.DataAccessException;

import java.sql.Connection;
import java.sql.SQLException;

import static java.util.Objects.isNull;

/**
 * alexandr.lebedko : 04.07.2017.
 */
public final class JdbcThreadLocalTransactionManager extends TransactionManager {
    private static final ThreadLocal<TransactionCounter> transactionCounter = new ThreadLocal<>();

    private final ThreadLocalConnectionProvider connectionProvider;

    public JdbcThreadLocalTransactionManager(ThreadLocalConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    protected void begin() throws DataAccessException {
        LOG.debug("Begin transaction invoked");

        if (isNull(transactionCounter.get())) {
            transactionCounter.set(new TransactionCounter());
            try {
                LOG.debug("Creating new connection");
                Connection connection = connectionProvider.getConnection();
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                LOG.error("Failed to create connection");
                throw new DataAccessException(e);
            }
        }

        transactionCounter.get().addTx();

        LOG.debug("Begin transaction method executed");
    }

    @Override
    protected void rollback() throws DataAccessException {
        LOG.debug("Transaction rollback invoked");
        try {
            connectionProvider.getConnection().rollback();
            connectionProvider.getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            LOG.error("Failed to rollback transaction");
            throw new DataAccessException(e);
        } finally {
            cleanUp();
        }
        LOG.debug("Transaction rollback executed");
    }

    @Override
    protected void commit() throws DataAccessException {
        LOG.debug("Commit method invoked");

        if (transactionCounter.get().isNestedTx()) {
            transactionCounter.get().removeTx();
            LOG.debug("Committing of nested transaction amended");
            return;
        }

        try {
            connectionProvider.getConnection().commit();
            connectionProvider.getConnection().setAutoCommit(true);
            LOG.debug("Committing initial transaction");
        } catch (SQLException e) {
            LOG.error("Failed to commit transaction");
            throw new DataAccessException(e);
        } finally {
            cleanUp();
        }


    }

    private void cleanUp() throws DataAccessException{
        LOG.debug("Cleaning up thread local resources: TransactionCounter and ThreadLocalConnectionProvider");
        try {
            LOG.debug("Closing connection");
            connectionProvider.getConnection().close();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to close connection");
        }

        connectionProvider.clearStorage();
        transactionCounter.remove();
    }
}
