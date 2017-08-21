package net.lebedko.service;

import net.lebedko.dao.UserDao;
import net.lebedko.dao.TransactionManager;
import net.lebedko.dao.jdbc.demo.CategoryDao;
import net.lebedko.service.demo.CategoryService;
import net.lebedko.service.demo.CategoryServiceImpl;
import net.lebedko.service.demo.FileService;
import net.lebedko.service.demo.FileServiceImpl;
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
        services.put(CategoryService.class, new CategoryServiceImpl(serviceTemplate, getDao(CategoryDao.class)));
        services.put(FileService.class, new FileServiceImpl());
    }

    public static <T> T getService(Class<T> clazz) {
        return (T) services.get(clazz);
    }
}
