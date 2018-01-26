package net.lebedko.service;

import net.lebedko.service.impl.*;

public interface ServiceFactory {

    InvoiceService getInvoiceService();

    OrderService getOrderService();

    ItemService getItemService();

    CategoryService getCategoryService();

    OrderItemService getOrderItemService();

    UserService getUserService();

    ImageService getImageService();

    static ServiceFactory getServiceFactory() {
        return ServiceFactoryImpl.getInstance();
    }
}
