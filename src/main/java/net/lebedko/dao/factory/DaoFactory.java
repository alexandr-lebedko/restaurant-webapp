package net.lebedko.dao.factory;

import net.lebedko.dao.GenericDao;
import net.lebedko.dao.transaction.TransactionManager;

/**
 * alexandr.lebedko : 08.05.2017.
 */
public interface DaoFactory {

    <T extends GenericDao> T getDao(Class<T> dao);

    TransactionManager getTxManager();
}
