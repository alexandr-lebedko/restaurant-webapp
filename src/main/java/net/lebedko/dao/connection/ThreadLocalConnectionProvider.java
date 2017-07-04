package net.lebedko.dao.connection;

import java.sql.Connection;
import java.sql.SQLException;

import static java.util.Objects.isNull;

/**
 * alexandr.lebedko : 04.07.2017.
 */
public class ThreadLocalConnectionProvider implements ConnectionProvider {
    private static final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal();
    private ConnectionProvider connectionProvider;

    public ThreadLocalConnectionProvider() {
        this(ConnectionProvider.getProvider());
    }

    public ThreadLocalConnectionProvider(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (isNull(connectionThreadLocal.get())) {
            Connection connection = connectionProvider.getConnection();
            connectionThreadLocal.set(connection);
        }

        return connectionThreadLocal.get();
    }

    public void clearStorage() {
        connectionThreadLocal.remove();
    }
}
