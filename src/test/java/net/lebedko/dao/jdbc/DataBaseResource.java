package net.lebedko.dao.jdbc;

import com.github.benoitduffezz.ScriptRunner;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import net.lebedko.dao.jdbc.connection.TestConnectionProvider;
import org.junit.rules.ExternalResource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static net.lebedko.util.PropertyUtil.loadProperties;

/**
 * alexandr.lebedko : 04.05.2017.
 */

public class DataBaseResource extends ExternalResource {
    private ScriptRunner scriptRunner;
    private BoneCP connectionPool;

    public DataBaseResource() {
        Properties props = loadProperties("test-connection-pool-config.properties");
        BoneCPConfig config = new BoneCPConfig();
        config.setUsername(props.getProperty("user"));
        config.setPassword(props.getProperty("password"));
        config.setJdbcUrl(props.getProperty("url"));
        try {
            connectionPool = new BoneCP(config);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void before() throws Throwable {
        Connection connection = connectionPool.getConnection();
        scriptRunner = new ScriptRunner(connection, false, false);
        InputStreamReader reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("test-setup-schema.sql"));
        scriptRunner.runScript(reader);
    }

    @Override
    protected void after() {
        try (Connection connection = connectionPool.getConnection()) {
            InputStreamReader reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("test-drop-tables.sql"));
            scriptRunner = new ScriptRunner(connection, false, false);
            scriptRunner.runScript(reader);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        connectionPool.close();
    }

    public TestConnectionProvider getConnectionProvider() throws SQLException {
        return new TestConnectionProvider(connectionPool.getConnection());
    }


}
