package net.lebedko.service;

import net.lebedko.dao.*;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.service.impl.*;

import java.util.HashMap;
import java.util.Map;

import static net.lebedko.dao.DaoFactory.*;

/**
 * alexandr.lebedko : 30.06.2017.
 */
public abstract class ServiceFactory {
    private static final Map<Class, Object> services = new HashMap<>();

    static {
        ServiceTemplate serviceTemplate = new ServiceTemplate(TransactionManager.getTxManager());

        services.put(UserService.class, new UserServiceImpl(
                serviceTemplate,
                getDao(UserDao.class)));

        services.put(CategoryService.class, new CategoryServiceImpl(
                serviceTemplate,
                getDao(CategoryDao.class)));

        services.put(FileService.class, new FileServiceImpl());

        services.put(ItemService.class, new ItemServiceImpl(
                serviceTemplate,
                getService(FileService.class),
                getDao(ItemDao.class)));


        services.put(InvoiceService.class, new InvoiceServiceImpl(getDao(InvoiceDao.class), serviceTemplate));

        //TODO:: remove circular dependency
        services.put(OrderService.class, new OrderServiceImpl(
                serviceTemplate,
                getService(InvoiceService.class),
                getService(ItemService.class),
                getDao(OrderDao.class),
                getDao(OrderItemDao.class)));


        ((InvoiceServiceImpl) getService(InvoiceService.class)).setOrderService(getService(OrderService.class));


    }

    public static <T> T getService(Class<T> clazz) {
        return (T) services.get(clazz);
    }
}
