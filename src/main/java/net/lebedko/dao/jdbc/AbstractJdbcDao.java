package net.lebedko.dao.jdbc;

import net.lebedko.dao.jdbc.connection.ConnectionProvider;
import net.lebedko.dao.jdbc.template.QueryTemplate;

import java.util.Properties;

import static net.lebedko.util.PropertyUtil.loadProperties;

/**
 * alexandr.lebedko : 02.10.2017.
 */
public class AbstractJdbcDao {
    protected static final Properties QUERIES = loadProperties("sql-queries.properties");

    protected QueryTemplate template;

    protected AbstractJdbcDao(QueryTemplate template) {
        this.template = template;
    }

    protected AbstractJdbcDao() {
        this(new QueryTemplate(ConnectionProvider.getProvider()));
    }
}
