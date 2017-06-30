package net.lebedko.dao;

import net.lebedko.dao.connection.BoneCPConnectionProvider;
import net.lebedko.dao.connection.ThreadLocalConnectionProvider;
import net.lebedko.dao.jdbc.*;
import net.lebedko.dao.template.QueryTemplate;
import net.lebedko.dao.template.errortranslator.MySqlExceptionTranslator;
import net.lebedko.entity.dish.Dish;
import net.lebedko.service.impl.UserServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * alexandr.lebedko : 30.06.2017.
 */
public abstract class DaoFactory {
    private static final Map<Class, Object> daos = new HashMap<>();

    static {
        QueryTemplate template = new QueryTemplate(new ThreadLocalConnectionProvider(new BoneCPConnectionProvider()),
                new MySqlExceptionTranslator());

        daos.put(UserDao.class, new JdbcUserDao(template));
        daos.put(DishDao.class, new JdbcDishDao(template));
        daos.put(InvoiceDao.class, new JdbcInvoiceDao(template));
        daos.put(MenuDao.class, new JdbcMenuDao(template));
        daos.put(OrderDao.class, new JdbcOrderDao(template));
    }

    public static <T> T getDao(Class<T> clazz) {
        return (T) daos.get(clazz);
    }

}
