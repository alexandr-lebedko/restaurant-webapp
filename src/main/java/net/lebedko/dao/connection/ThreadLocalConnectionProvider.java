package net.lebedko.dao.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

import static java.util.Objects.requireNonNull;

/**
 * alexandr.lebedko : 08.05.2017.
 */

public class ThreadLocalConnectionProvider implements ConnectionProvider {
    private static final Logger logger = LogManager.getLogger();
    private static final ThreadLocal<Connection> threadLocalConnection = new ThreadLocal<>();

    private ConnectionProvider connectionProvider;

    public ThreadLocalConnectionProvider(ConnectionProvider connectionProvider) {
        this.connectionProvider = requireNonNull(connectionProvider,
                "Connection Provider cannot be null!");
    }

    @Override
    public Connection getConnection() throws SQLException {
        logger.info("Connection requested");

        if (threadLocalConnection.get() == null) {
            Connection connection = requireNonNull(connectionProvider.getConnection(), "Connection cannot be null!");
            threadLocalConnection.set(connection);

            logger.info("New connection instance is set: " + connection);
        }
        return new ThreadLocalConnectionWrapper(this.threadLocalConnection);
    }

}
