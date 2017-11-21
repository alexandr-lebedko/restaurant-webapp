package net.lebedko.dao.jdbc.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * alexandr.lebedko : 30.06.2017.
 */
public class JndiConnectionProvider implements ConnectionProvider {
    private static final Logger LOG = LogManager.getLogger();
    private DataSource dataSource;

    public JndiConnectionProvider() {
        LOG.debug("Begin initializing of class");
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/restaurant");
            createTestConnection();
            LOG.debug("DataSource created");
        } catch (NamingException e) {
            LOG.error("Failed to initialize DataSource");
            throw new RuntimeException("Failed to initialize DataSource", e);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }


    private void createTestConnection() {
        LOG.debug("Attempt to create test connection");
        try (Connection connection = getConnection()) {
            LOG.debug("Test connection created");
        } catch (SQLException e) {
            LOG.error("Failed to create test connection", e);
            throw new RuntimeException("Failed to create test connection", e);
        }
    }
}
