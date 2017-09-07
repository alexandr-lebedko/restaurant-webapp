package net.lebedko.dao;

import net.lebedko.dao.jdbc.connection.ThreadLocalConnectionProvider;
import net.lebedko.dao.jdbc.*;
import net.lebedko.dao.jdbc.demo.CategoryDao;
import net.lebedko.dao.jdbc.demo.JdbcCategoryDao;
import net.lebedko.dao.jdbc.demo.JdbcMenuItemDao;
import net.lebedko.dao.jdbc.demo.MenuItemDao;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.dao.jdbc.template.errortranslator.MySqlExceptionTranslator;

import java.util.HashMap;
import java.util.Map;

/**
 * alexandr.lebedko : 30.06.2017.
 */
public abstract class DaoFactory {
    private static final Map<Class, Object> daos = new HashMap<>();

    static {
        ThreadLocalConnectionProvider connectionProvider = new ThreadLocalConnectionProvider();

        QueryTemplate template = new QueryTemplate(connectionProvider,new MySqlExceptionTranslator());

        daos.put(UserDao.class, new JdbcUserDao(template));
        daos.put(DishDao.class, new JdbcDishDao(template));
        daos.put(InvoiceDao.class, new JdbcInvoiceDao(template));
        daos.put(MenuDao.class, new JdbcMenuDao(template));
        daos.put(OrderDao.class, new JdbcOrderDao(template));


        daos.put(CategoryDao.class, new JdbcCategoryDao(template));
        daos.put(MenuItemDao.class, new JdbcMenuItemDao(template));

    }

    public static <T> T getDao(Class<T> clazz) {
        return (T) daos.get(clazz);
    }

}
