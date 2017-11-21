package net.lebedko.dao.jdbc;

import net.lebedko.dao.*;
<<<<<<< HEAD
import net.lebedko.dao.jdbc.connection.ConnectionProvider;
import net.lebedko.dao.jdbc.connection.JndiConnectionProvider;
import net.lebedko.dao.jdbc.connection.ThreadLocalConnectionProvider;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.dao.jdbc.transaction.JdbcThreadLocalTransactionManager;

public class JdbcDaoFactory implements DaoFactory {
    private static final DaoFactory INSTANCE = new JdbcDaoFactory();

    public static DaoFactory getInstance() {
        return INSTANCE;
    }

    private ItemDao itemDao;
    private CategoryDao categoryDao;
    private OrderDao orderDao;
    private InvoiceDao invoiceDao;
    private OrderItemDao orderItemDao;
    private UserDao userDao;
    private TransactionManager txManager;

    private JdbcDaoFactory() {
        ThreadLocalConnectionProvider connectionProvider = new ThreadLocalConnectionProvider(new JndiConnectionProvider());
        QueryTemplate template = new QueryTemplate(connectionProvider);

        this.txManager = new JdbcThreadLocalTransactionManager(connectionProvider);
        this.itemDao = new JdbcItemDao(template);
        this.categoryDao = new JdbcCategoryDao(template);
        this.orderDao = new JdbcOrderDao(template);
        this.invoiceDao = new JdbcInvoiceDao(template);
        this.userDao = new JdbcUserDao(template);
        this.orderItemDao = new JdbcOrderItemDao(template);
=======

public class JdbcDaoFactory implements DaoFactory {

    private JdbcDaoFactory() {
>>>>>>> master
    }

    @Override
    public ItemDao getItemDao() {
<<<<<<< HEAD
        return this.itemDao;
=======
        return new JdbcItemDao();
>>>>>>> master
    }

    @Override
    public CategoryDao getCategoryDao() {
<<<<<<< HEAD
        return this.categoryDao;
=======
        return new JdbcCategoryDao();
>>>>>>> master
    }

    @Override
    public OrderDao getOrderDao() {
<<<<<<< HEAD
        return this.orderDao;
=======
        return new JdbcOrderDao();
>>>>>>> master
    }

    @Override
    public InvoiceDao getInvoiceDao() {
<<<<<<< HEAD
        return this.invoiceDao;
=======
        return new JdbcInvoiceDao();
>>>>>>> master
    }

    @Override
    public OrderItemDao getOrderItemDao() {
<<<<<<< HEAD
        return this.orderItemDao;
=======
        return new JdbcOrderItemDao();
>>>>>>> master
    }

    @Override
    public UserDao getUserDao() {
<<<<<<< HEAD
        return this.userDao;
    }

    public TransactionManager getTxManager() {
        return txManager;
=======
        return new JdbcUserDao();
    }

    private static final DaoFactory INSTANCE = new JdbcDaoFactory();

    public static DaoFactory getInstance() {
        return INSTANCE;
>>>>>>> master
    }
}
