package net.lebedko.service.impl;

import net.lebedko.dao.DaoFactory;
import net.lebedko.dao.TransactionManager;
<<<<<<< HEAD
import net.lebedko.dao.jdbc.connection.ThreadLocalConnectionProvider;
import net.lebedko.dao.jdbc.transaction.JdbcThreadLocalTransactionManager;
=======
>>>>>>> master
import net.lebedko.service.*;

public class ServiceFactoryImpl implements ServiceFactory {
    private static final ServiceFactory INSTANCE = new ServiceFactoryImpl();

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    private DaoFactory daoFactory;
    private ServiceTemplate serviceTemplate;
    private UserService userService;
    private CategoryService categoryService;
    private ItemService itemService;
    private OrderItemService orderItemService;
    private OrderService orderService;
    private InvoiceService invoiceService;
    private FileService fileService;

    private ServiceFactoryImpl() {
        this.daoFactory = DaoFactory.getInstance();
<<<<<<< HEAD
        this.serviceTemplate = new ServiceTemplate(daoFactory.getTxManager());

=======
        this.serviceTemplate = new ServiceTemplate();
>>>>>>> master
        this.fileService = new FileServiceImpl();
        this.userService = new UserServiceImpl(serviceTemplate, daoFactory.getUserDao());
        this.itemService = new ItemServiceImpl(serviceTemplate, new FileServiceImpl(), daoFactory.getItemDao());
        this.categoryService = new CategoryServiceImpl(serviceTemplate, daoFactory.getCategoryDao());
        this.orderItemService = new OrderItemServiceImpl(serviceTemplate, daoFactory.getOrderItemDao());
        this.invoiceService = new InvoiceServiceImpl(serviceTemplate, daoFactory.getInvoiceDao());
<<<<<<< HEAD
        this.orderService = new OrderServiceImpl(serviceTemplate, invoiceService, orderItemService, itemService, daoFactory.getOrderDao());
=======
        this.orderService = new OrderServiceImpl(serviceTemplate, invoiceService, orderItemService, daoFactory.getOrderDao());
>>>>>>> master
        ((InvoiceServiceImpl) invoiceService).setOrderService(orderService);
    }

    @Override
    public InvoiceService getInvoiceService() {
        return invoiceService;
    }

    @Override
    public OrderService getOrderService() {
        return orderService;
    }

    @Override
    public ItemService getItemService() {
        return itemService;
    }

    @Override
    public CategoryService getCategoryService() {
        return categoryService;
    }

    @Override
    public OrderItemService getOrderItemService() {
        return orderItemService;
    }

    @Override
    public UserService getUserService() {
        return userService;
    }

    @Override
    public FileService getFileService() {
        return fileService;
    }
}
