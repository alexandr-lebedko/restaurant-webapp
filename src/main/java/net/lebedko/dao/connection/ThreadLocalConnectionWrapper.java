package net.lebedko.dao.connection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * alexandr.lebedko : 08.05.2017.
 */

public class ThreadLocalConnectionWrapper extends BaseConnectionWrapper {
    private ThreadLocal<Connection> threadLocalConnection;

    public ThreadLocalConnectionWrapper(ThreadLocal<Connection> threadLocalConnection) {
        super(threadLocalConnection.get());
        this.threadLocalConnection = threadLocalConnection;
    }

    @Override
    public void close() throws SQLException {
        super.close();
        threadLocalConnection.remove();
    }
}
