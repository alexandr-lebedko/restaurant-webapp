package net.lebedko.dao.jdbc.connection;

import java.sql.Connection;
import java.sql.SQLException;

import static java.util.Objects.requireNonNull;

/**
 * alexandr.lebedko : 08.05.2017.
 */

public class ThreadLocalConnectionProvider implements ConnectionProvider {
    private ConnectionProvider connectionProvider;
    private ThreadLocal<Connection> threadLocalConnection = new ThreadLocal<>();

    public ThreadLocalConnectionProvider(ConnectionProvider connectionProvider) {
        this.connectionProvider = requireNonNull(connectionProvider,
                "Connection Provider cannot be null!");
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (threadLocalConnection.get() == null) {
            Connection connection = requireNonNull(connectionProvider.getConnection(),
                    "Connection cannot be null!");
            threadLocalConnection.set(connection);
        }
        return new ThreadLocalConnectionWrapper(this.threadLocalConnection);
    }

}
