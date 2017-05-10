package net.lebedko.dao.connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * alexandr.lebedko : 08.05.2017.
 */

public class JndiConnectionProvider implements ConnectionProvider {

    private static final JndiConnectionProvider instance = new JndiConnectionProvider();
    private DataSource dataSource;

    public static JndiConnectionProvider getInstance() {
        return instance;
    }

    private JndiConnectionProvider() {
        try {
            InitialContext initialContext = new InitialContext();
            Context envContext = (Context) initialContext.lookup("java:comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/db");

        } catch (NamingException e) {
            throw new RuntimeException("Cannot look up DataSource through JNDI", e);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
