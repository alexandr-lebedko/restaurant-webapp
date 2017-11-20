package net.lebedko.service.impl;

import net.lebedko.dao.DaoFactory;
import net.lebedko.dao.TransactionManager;
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
        this.serviceTemplate = new ServiceTemplate();
        this.fileService = new FileServiceImpl();
        this.userService = new UserServiceImpl(serviceTemplate, daoFactory.getUserDao());
        this.itemService = new ItemServiceImpl(serviceTemplate, new FileServiceImpl(), daoFactory.getItemDao());
        this.categoryService = new CategoryServiceImpl(serviceTemplate, daoFactory.getCategoryDao());
        this.orderItemService = new OrderItemServiceImpl(serviceTemplate, daoFactory.getOrderItemDao());
        this.invoiceService = new InvoiceServiceImpl(serviceTemplate, daoFactory.getInvoiceDao());
        this.orderService = new OrderServiceImpl(serviceTemplate, invoiceService, orderItemService, daoFactory.getOrderDao());
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
