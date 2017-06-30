package net.lebedko.dao.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * alexandr.lebedko : 08.05.2017.
 */

public class ThreadLocalConnectionWrapper extends BaseConnectionWrapper {
    private static final Logger logger = LogManager.getLogger();

    private ThreadLocal<Connection> threadLocalConnection;

    public ThreadLocalConnectionWrapper(ThreadLocal<Connection> threadLocalConnection) {
        super(threadLocalConnection.get());
        this.threadLocalConnection = threadLocalConnection;

        logger.debug("New instance is created");
    }

    @Override
    public void close() throws SQLException {
        super.close();

        logger.debug("Connection: " + threadLocalConnection.get() + " closed and removed from thread local storage");

        threadLocalConnection.remove();

    }
}
