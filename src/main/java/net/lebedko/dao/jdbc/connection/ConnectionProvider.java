package net.lebedko.dao.jdbc.connection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * alexandr.lebedko : 08.05.2017.
 */

public interface ConnectionProvider {

    Connection getConnection() throws SQLException;

    static ConnectionProvider getProvider() {
        return new JndiConnectionProvider();
    }
}
