package net.lebedko.dao.jdbc.transaction;

import net.lebedko.dao.TransactionManager;
import net.lebedko.dao.jdbc.connection.JndiConnectionProvider;
import net.lebedko.dao.jdbc.connection.ThreadLocalConnectionProvider;
import net.lebedko.dao.exception.DataAccessException;

import java.sql.Connection;
import java.sql.SQLException;

import static java.util.Objects.isNull;

/**
 * alexandr.lebedko : 04.07.2017.
 */
//TODO :: REFACTOR TO STATE PATTER
public final class JdbcThreadLocalTransactionManager extends TransactionManager {
    private static final ThreadLocal<TransactionCounter> transactionCounter = new ThreadLocal<>();
    private final ThreadLocalConnectionProvider connectionProvider;

    public JdbcThreadLocalTransactionManager(ThreadLocalConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public JdbcThreadLocalTransactionManager(){
        this(new ThreadLocalConnectionProvider(new JndiConnectionProvider()));
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
        if (isNull(transactionCounter.get())) {
            LOG.debug("Transaction rollback was executed earlier");
            return;
        }

        try {
            connectionProvider.getConnection().rollback();
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
            LOG.debug("Committing initial transaction");
        } catch (SQLException e) {
            LOG.error("Failed to commit transaction");
            throw new DataAccessException(e);
        } finally {
            cleanUp();
        }


    }

    private void cleanUp() throws DataAccessException {
        LOG.debug("Cleaning up thread local resources: TransactionCounter and ThreadLocalConnectionProvider");
        try {
            LOG.debug("Closing connection");
            connectionProvider.getConnection().setAutoCommit(true);
            connectionProvider.getConnection().close();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to close connection");
        }

        connectionProvider.clearStorage();
        transactionCounter.remove();
    }
}
