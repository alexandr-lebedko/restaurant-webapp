package net.lebedko.dao.connection;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.PropertiesUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static net.lebedko.util.PropertyUtil.loadProperties;

/**
 * alexandr.lebedko : 30.06.2017.
 */
public class BoneCPConnectionProvider implements ConnectionProvider {
    private static final Logger logger = LogManager.getLogger();
    private BoneCP cp;

    public BoneCPConnectionProvider() {
        Properties props = loadProperties("connection-pool-config.properties");
        BoneCPConfig config = new BoneCPConfig();
        config.setUsername(props.getProperty("user"));
        config.setPassword(props.getProperty("password"));
        config.setJdbcUrl(props.getProperty("url"));

        try {
            cp = new BoneCP(config);
        } catch (SQLException e) {
            logger.error("Failed to create BoneCP connection pool :", e);
            throw new RuntimeException("Could not create connection pool", e);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        logger.debug("New connection requested");
        return cp.getConnection();
    }
}
