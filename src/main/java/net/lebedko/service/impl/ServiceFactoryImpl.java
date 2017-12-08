package net.lebedko.service.impl;

import net.lebedko.dao.DaoFactory;
import net.lebedko.dao.TransactionManager;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.CategoryService;
import net.lebedko.service.ItemService;
import net.lebedko.service.OrderService;
import net.lebedko.service.UserService;
import net.lebedko.service.OrderItemService;
import net.lebedko.service.ServiceFactory;
import net.lebedko.service.FileService;

public class ServiceFactoryImpl implements ServiceFactory {
    private static final ServiceFactory INSTANCE = new ServiceFactoryImpl();

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    private UserService userService;
    private CategoryService categoryService;
    private ItemService itemService;
    private OrderItemService orderItemService;
    private OrderService orderService;
    private InvoiceService invoiceService;
    private FileService fileService;

    private ServiceFactoryImpl() {
        DaoFactory daoFactory = DaoFactory.getInstance();
        TransactionManager txManager = daoFactory.getTxManager();

        this.fileService = new FileServiceImpl();
        this.userService = new UserServiceImpl(txManager, daoFactory.getUserDao());
        this.itemService = new ItemServiceImpl(txManager, new FileServiceImpl(), daoFactory.getItemDao());
        this.categoryService = new CategoryServiceImpl(txManager, daoFactory.getCategoryDao());
        this.orderItemService = new OrderItemServiceImpl(txManager, daoFactory.getOrderItemDao());
        this.invoiceService = new InvoiceServiceImpl(txManager, daoFactory.getInvoiceDao());

        this.orderService = new OrderServiceImpl(txManager, invoiceService, orderItemService, daoFactory.getOrderDao());
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
