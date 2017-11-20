package net.lebedko.service;

import net.lebedko.dao.*;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.service.impl.*;

import java.util.HashMap;
import java.util.Map;

import static net.lebedko.dao.DaoFactory.*;

/**
 * alexandr.lebedko : 30.06.2017.
 */
public interface ServiceFactory {

    InvoiceService getInvoiceService();

    OrderService getOrderService();

    ItemService getItemService();

    CategoryService getCategoryService();

    OrderItemService getOrderItemService();

    UserService getUserService();

    FileService getFileService();

    static ServiceFactory getServiceFactory() {
        return ServiceFactoryImpl.getInstance();
    }
}
