package net.lebedko.service;

import net.lebedko.dao.DaoFactory;
import net.lebedko.dao.UserDao;
import net.lebedko.dao.transaction.TransactionManager;
import net.lebedko.service.impl.ServiceTemplate;
import net.lebedko.service.impl.UserServiceImpl;

import java.util.HashMap;
import java.util.Map;

import static net.lebedko.dao.DaoFactory.*;

/**
 * alexandr.lebedko : 30.06.2017.
 */
public abstract class ServiceFactory {
    private static final Map<Class, Object> services = new HashMap();

    static {
        ServiceTemplate serviceTemplate = new ServiceTemplate(TransactionManager.getTxManager());

        services.put(UserService.class, new UserServiceImpl(serviceTemplate, getDao(UserDao.class)));
    }

   public static <T> T getService(Class<T> clazz) {
        return (T) services.get(clazz);
    }
}
