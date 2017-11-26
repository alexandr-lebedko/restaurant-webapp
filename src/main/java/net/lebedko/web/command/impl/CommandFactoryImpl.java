package net.lebedko.web.command.impl;

import net.lebedko.service.*;
import net.lebedko.web.command.ICommand;
import net.lebedko.web.command.ICommandFactory;
import net.lebedko.web.command.impl.admin.*;
import net.lebedko.web.command.impl.auth.*;
import net.lebedko.web.command.impl.client.*;
import net.lebedko.web.validator.user.UserValidator;

import java.util.HashMap;
import java.util.Map;

import static net.lebedko.web.util.constant.WebConstant.COMMAND.*;

public class CommandFactoryImpl implements ICommandFactory {

    private Map<String, ICommand> commandMap = new HashMap<>();

    public CommandFactoryImpl() {
        final ServiceFactory serviceFactory = ServiceFactory.getServiceFactory();
        final UserService userService = serviceFactory.getUserService();
        final OrderService orderService = serviceFactory.getOrderService();
        final InvoiceService invoiceService = serviceFactory.getInvoiceService();
        final OrderItemService orderItemService = serviceFactory.getOrderItemService();
        final ItemService itemService = serviceFactory.getItemService();
        final CategoryService categoryService = serviceFactory.getCategoryService();

        commandMap.put(GET_SIGN_IN, new SignInGetCommand());
        commandMap.put(POST_SIGN_IN, new SignInPostCommand(userService));
        commandMap.put(GET_SIGN_UP, new SignUpGetCommand());
        commandMap.put(POST_SIGN_UP, new SignUpPostCommand(userService, new UserValidator()));
        commandMap.put(SIGN_OUT, new SignOutCommand());

        commandMap.put(CLIENT_GET_ORDER, new ClientGetOrderCommand(orderService, orderItemService));
        commandMap.put(CLIENT_GET_ORDERS, new ClientGetOrdersCommand(orderService));
        commandMap.put(CLIENT_CREATE_ORDER, new ClientCreateOrderCommand(orderService));
        commandMap.put(CLIENT_GET_ORDER_FORM, new OrderGetCommand());
        commandMap.put(CLIENT_GET_INVOICE, new ClientGetInvoiceCommand(invoiceService, orderItemService));
        commandMap.put(CLIENT_GET_INVOICES, new ClientGetInvoicesCommand(invoiceService));
        commandMap.put(CLIENT_ADD_ITEM_TO_BUCKET, new ClientAddItemToOrderCommand(itemService));
        commandMap.put(CLIENT_CLEAR_ORDER_BUCKET, new ClientDeleteBucketCommand());
        commandMap.put(CLIENT_GET_MENU, new ClientGetMenuCommand(categoryService, itemService));
        commandMap.put(CLIENT_REJECT_ORDER, new ClientRejectModifiedOrderCommand(orderService));
        commandMap.put(CLIENT_MODIFY_ORDER, new ClientModifyOrderCommand(orderService, orderItemService));
        commandMap.put(CLIENT_SUBMIT_MODIFIED_ORDER, new ClientSubmitModifiedOrderCommand(orderService));
        commandMap.put(CLIENT_PAY_INVOICE, new ClientPayInvoiceCommand(invoiceService, orderItemService));

        commandMap.put(ADMIN_GET_MAIN, new AdminGetNewOrdersCommand(orderService, invoiceService));
        commandMap.put(GET_ADMIN_NEW_ORDERS, new AdminGetNewOrdersCommand(orderService, invoiceService));
        commandMap.put(GET_ADMIN_ORDER_DETAILS, new AdminGetOrderCommandCommand(orderService, orderItemService, invoiceService));
        commandMap.put(ADMIN_PROCESS_ORDER, new AdminProcessOrderCommand(orderService, invoiceService));
        commandMap.put(ADMIN_REJECT_ORDER, new AdminRejectOrderCommand(orderService, invoiceService));
        commandMap.put(ADMIN_MODIFY_ORDER, new AdminModifyOrderCommand(orderService, invoiceService));
        commandMap.put(ADMIN_GET_PROCESSED_ORDERS, new AdminGetProcessedOrdersCommand(orderService, invoiceService));
        commandMap.put(ADMIN_GET_REJECTED_ORDERS, new AdminGetRejectedOrdersCommand(orderService, invoiceService));
        commandMap.put(ADMIN_GET_MODIFIED_ORDERS, new AdminGetModifiedOrdersCommand(orderService, invoiceService));
        commandMap.put(ADMIN_GET_PAID_INVOICES, new AdminGetPaidInvoicesCommand(orderService, invoiceService));
        commandMap.put(ADMIN_GET_UNPAID_INVOICES, new AdminGetUnpaidInvoicesCommand(orderService, invoiceService));
        commandMap.put(ADMIN_GET_INVOICE, new AdminGetInvoiceCommand(orderService, orderItemService, invoiceService));
        commandMap.put(ADMIN_GET_CATEGORIES, new AdminGetCategoriesCommand(orderService, invoiceService, categoryService));
        commandMap.put(ADMIN_MODIFY_CATEGORY, new AdminModifyCategoryCommand(orderService, invoiceService, categoryService));
        commandMap.put(ADMIN_CREATE_CATEGORY, new AdminCreateNewCategoryCommand(orderService, invoiceService, categoryService));
        commandMap.put(ADMIN_DELETE_CATEGORY, new AdminDeleteCategoryCommand(orderService, invoiceService, categoryService));
        commandMap.put(ADMIN_GET_ITEMS, new AdminGetItemsCommand(orderService, invoiceService, itemService, categoryService));
        commandMap.put(ADMIN_MODIFY_ITEM, new AdminModifyItemCommand(orderService, invoiceService, itemService, categoryService));
        commandMap.put(ADMIN_MODIFY_ITEM_IMAGE, new AdminModifyItemImageCommand(orderService, invoiceService, itemService, categoryService));
        commandMap.put(ADMIN_CREATE_ITEM, new AdminCreateItemCommand(orderService, invoiceService, itemService, categoryService));
        commandMap.put(ADMIN_ITEM_FORM, new AdminGetItemFormCommand(orderService, invoiceService, categoryService));
    }

    @Override
    public ICommand getCommand(String cmd) {
        return commandMap.get(cmd);
    }

}
