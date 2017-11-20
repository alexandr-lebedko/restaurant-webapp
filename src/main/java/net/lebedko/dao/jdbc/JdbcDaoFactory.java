package net.lebedko.dao.jdbc;

import net.lebedko.dao.*;

public class JdbcDaoFactory implements DaoFactory {

    private JdbcDaoFactory() {
    }

    @Override
    public ItemDao getItemDao() {
        return new JdbcItemDao();
    }

    @Override
    public CategoryDao getCategoryDao() {
        return new JdbcCategoryDao();
    }

    @Override
    public OrderDao getOrderDao() {
        return new JdbcOrderDao();
    }

    @Override
    public InvoiceDao getInvoiceDao() {
        return new JdbcInvoiceDao();
    }

    @Override
    public OrderItemDao getOrderItemDao() {
        return new JdbcOrderItemDao();
    }

    @Override
    public UserDao getUserDao() {
        return new JdbcUserDao();
    }

    private static final DaoFactory INSTANCE = new JdbcDaoFactory();

    public static DaoFactory getInstance() {
        return INSTANCE;
    }
}
