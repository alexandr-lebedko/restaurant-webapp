package net.lebedko.dao.jdbc.connection;

import net.lebedko.dao.jdbc.connection.ConnectionProvider;
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
    private static final Logger logger = LogManager.getLogger();

    private DataSource dataSource;

    public JndiConnectionProvider() {
        logger.debug("Begin initializing of class");
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/restaurant");
            createTestConnection();
            logger.debug("DataSource created");
        } catch (NamingException e) {
            logger.error("Failed to initialize DataSource");
            throw new RuntimeException("Failed to initialize DataSource", e);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }


    private void createTestConnection() {
        logger.debug("Attempt to create test connection");
        try (Connection connection = getConnection()) {
            logger.debug("Test connection created");
        } catch (SQLException e) {
            logger.error("Failed to create test connection",e);
            throw new RuntimeException("Failed to create test connection",e);
        }
    }
}
