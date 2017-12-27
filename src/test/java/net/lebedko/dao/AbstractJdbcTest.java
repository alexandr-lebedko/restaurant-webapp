package net.lebedko.dao;

import net.lebedko.dao.jdbc.template.QueryTemplate;
import org.dbunit.JdbcBasedDBTestCase;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;


public abstract class AbstractJdbcTest extends JdbcBasedDBTestCase {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/restaurant-test?user=root&password=root&useSSL=false";
    private static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

    protected QueryTemplate queryTemplate;
    private Connection connection;

    public void setUp() throws Exception {
        super.setUp();
        connection = getConnection().getConnection();
        this.queryTemplate = new QueryTemplate(() -> connection);
    }

    public void tearDown() throws Exception {
        super.tearDown();
        connection.close();
    }

    @Override
    protected String getConnectionUrl() {
        return CONNECTION_URL;
    }

    @Override
    protected String getDriverClass() {
        return DRIVER_CLASS_NAME;
    }

}

