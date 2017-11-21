package net.lebedko.dao.jdbc;

import net.lebedko.dao.*;
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
    }

    @Override
    public ItemDao getItemDao() {
        return this.itemDao;
    }

    @Override
    public CategoryDao getCategoryDao() {
        return this.categoryDao;
    }

    @Override
    public OrderDao getOrderDao() {
        return this.orderDao;
    }

    @Override
    public InvoiceDao getInvoiceDao() {
        return this.invoiceDao;
    }

    @Override
    public OrderItemDao getOrderItemDao() {
        return this.orderItemDao;
    }

    @Override
    public UserDao getUserDao() {
        return this.userDao;
    }

    public TransactionManager getTxManager() {
        return txManager;
    }
}
