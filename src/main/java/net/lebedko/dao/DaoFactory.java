package net.lebedko.dao;

import net.lebedko.dao.jdbc.*;


public interface DaoFactory {
    ItemDao getItemDao();

    CategoryDao getCategoryDao();

    OrderDao getOrderDao();

    InvoiceDao getInvoiceDao();

    OrderItemDao getOrderItemDao();

    UserDao getUserDao();

    TransactionManager getTxManager();

    static DaoFactory getInstance() {
        return JdbcDaoFactory.getInstance();
    }
}
