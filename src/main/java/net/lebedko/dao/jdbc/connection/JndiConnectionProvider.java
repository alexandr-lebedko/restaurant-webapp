package net.lebedko.dao.jdbc.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JndiConnectionProvider implements ConnectionProvider {
    private static final String DATA_SOURCE_NAME = "jdbc/restaurant";
    private static final Logger LOG = LogManager.getLogger();
    private DataSource dataSource;

    public JndiConnectionProvider() {
        LOG.debug("Begin initializing of class");
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            dataSource = (DataSource) envContext.lookup(DATA_SOURCE_NAME);
            LOG.debug("DataSource created");
        } catch (NamingException e) {
            LOG.error("Failed to initialize DataSource", e);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
