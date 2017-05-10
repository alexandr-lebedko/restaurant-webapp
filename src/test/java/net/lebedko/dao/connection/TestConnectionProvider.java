package net.lebedko.dao.connection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * alexandr.lebedko : 08.05.2017.
 */

public class TestConnectionProvider implements ConnectionProvider {
    private Connection connection;

    public TestConnectionProvider(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return connection;
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }
}
