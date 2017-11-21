package net.lebedko.dao;

import net.lebedko.dao.jdbc.connection.ThreadLocalConnectionProvider;
import net.lebedko.dao.jdbc.*;
import net.lebedko.dao.jdbc.JdbcCategoryDao;
import net.lebedko.dao.jdbc.JdbcItemDao;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.dao.jdbc.template.errortranslator.MySqlExceptionTranslator;
import net.lebedko.entity.order.OrderItem;

import java.util.HashMap;
import java.util.Map;

/**
 * alexandr.lebedko : 30.06.2017.
 */
public interface DaoFactory {
    ItemDao getItemDao();

    CategoryDao getCategoryDao();

    OrderDao getOrderDao();

    InvoiceDao getInvoiceDao();

    OrderItemDao getOrderItemDao();

    UserDao getUserDao();

<<<<<<< HEAD
    TransactionManager getTxManager();

=======
>>>>>>> master
    static DaoFactory getInstance() {
        return JdbcDaoFactory.getInstance();
    }
}
