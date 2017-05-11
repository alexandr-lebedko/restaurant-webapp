package net.lebedko.dao.transaction;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.connection.ConnectionProvider;
import net.lebedko.dao.exception.TransactionException;

import java.sql.Connection;
import java.sql.SQLException;

import static java.util.Objects.requireNonNull;

/**
 * alexandr.lebedko : 08.05.2017.
 */

public class TransactionManagerImpl implements TransactionManager {

    private ConnectionProvider connectionProvider;
    private Connection connection;

    public TransactionManagerImpl(ConnectionProvider connectionProvider) {
        this.connectionProvider = requireNonNull(connectionProvider, "Connection Provider cannot be null!");
    }


    public void begin() throws TransactionException {
        try {
            getConnection().setAutoCommit(true);
        } catch (DataAccessException | SQLException e) {
            throw new TransactionException("Failed to begin transaction!", e);
        }
    }

    public void rollback() throws TransactionException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new TransactionException("Failed to rollback transaction!", e);
        }
    }

    public void commit() throws TransactionException, DataAccessException {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new TransactionException("Failed to commit transaction!", e);
        } finally {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                throw new DataAccessException("Failed to close connection", e);
            }
        }
    }

    private Connection getConnection() throws DataAccessException {
        if (connection == null) {
            try {
                connection = requireNonNull(connectionProvider.getConnection(),
                        "Connection cannot be null!");
            } catch (SQLException e) {
                throw new DataAccessException("Failed to retrieve connection!", e);
            }
        }
        return connection;
    }
}
