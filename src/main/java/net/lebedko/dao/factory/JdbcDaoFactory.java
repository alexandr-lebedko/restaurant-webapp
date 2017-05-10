package net.lebedko.dao.factory;

import net.lebedko.dao.*;
import net.lebedko.dao.jdbc.*;
import net.lebedko.dao.connection.ConnectionProvider;
import net.lebedko.dao.connection.JndiConnectionProvider;
import net.lebedko.dao.connection.ThreadLocalConnectionProvider;
import net.lebedko.dao.template.QueryTemplate;
import net.lebedko.dao.transaction.TransactionManager;
import net.lebedko.dao.transaction.TransactionManagerImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * alexandr.lebedko : 08.05.2017.
 */

public class JdbcDaoFactory implements DaoFactory {

    private static final JdbcDaoFactory instance = new JdbcDaoFactory();
    private Map<Class, Object> map = new HashMap<>();

    public static JdbcDaoFactory getInstance() {
        return instance;
    }

    private JdbcDaoFactory() {
        JndiConnectionProvider jndiConnectionProvider = JndiConnectionProvider.getInstance();
        ConnectionProvider connectionProvider = new ThreadLocalConnectionProvider(jndiConnectionProvider);
        QueryTemplate template = new QueryTemplate(connectionProvider);

        put(DishDao.class, new JdbcDishDao(template));
        put(UserDao.class, new JdbcUserDao(template));
        put(MenuDao.class, new JdbcMenuDao(template));
        put(OrderDao.class, new JdbcOrderDao(template));
        put(InvoiceDao.class, new JdbcInvoiceDao(template));
        put(TransactionManager.class, new TransactionManagerImpl(connectionProvider));
    }

    private <T> void put(Class<T> clazz, T instance) {
        map.put(clazz, instance);
    }

    @SuppressWarnings("unchecked")
    private <T> T get(Class<T> clazz) {
        return (T) map.get(clazz);
    }

    @Override
    public <T extends GenericDao> T getDao(Class<T> dao) {
        return get(dao);
    }

    @Override
    public TransactionManager getTxManager() {
        return get(TransactionManager.class);
    }
}
