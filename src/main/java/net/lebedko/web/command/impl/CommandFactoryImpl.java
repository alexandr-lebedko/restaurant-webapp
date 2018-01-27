package net.lebedko.web.command.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import net.lebedko.service.*;
import net.lebedko.web.command.*;
import net.lebedko.web.command.impl.client.*;
import net.lebedko.web.command.impl.admin.*;
import net.lebedko.web.command.impl.auth.*;
import net.lebedko.web.validator.user.UserValidator;

import static net.lebedko.web.util.constant.WebConstant.COMMAND.*;
import static java.util.Optional.ofNullable;

public class CommandFactoryImpl implements CommandFactory {
    private static final CommandFactory INSTANCE = new CommandFactoryImpl();

    public static CommandFactory getInstance() {
        return INSTANCE;
    }

    private Map<String, Command> commandMap = new HashMap<>();

    private CommandFactoryImpl() {
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
        commandMap.put(CLIENT_GET_ORDER_FORM, new ClientGetOrderFormCommand());
        commandMap.put(CLIENT_GET_INVOICE, new ClientGetInvoiceCommand(invoiceService, orderItemService));
        commandMap.put(CLIENT_GET_INVOICES, new ClientGetInvoicesCommand(invoiceService));
        commandMap.put(CLIENT_ADD_ITEM_TO_BUCKET, new ClientAddItemToOrderCommand(itemService));
        commandMap.put(CLIENT_CLEAR_ORDER_BUCKET, new ClientDeleteBucketCommand());
        commandMap.put(CLIENT_GET_MENU, new ClientGetMenuCommand(categoryService, itemService));
        commandMap.put(CLIENT_REJECT_ORDER, new ClientDeletedOrderCommand(orderService));
        commandMap.put(CLIENT_MODIFY_ORDER, new ClientModifyOrderCommand(orderService, orderItemService));
        commandMap.put(CLIENT_SUBMIT_MODIFIED_ORDER, new ClientSubmitModifiedOrderCommand(orderService));
        commandMap.put(CLIENT_PAY_INVOICE, new ClientPayInvoiceCommand(invoiceService, orderItemService));

        commandMap.put(ADMIN_GET_ORDERS, new AdminGetOrdersCommand(orderService));
        commandMap.put(ADMIN_PROCESS_ORDER, new AdminProcessOrderCommand(orderService));
        commandMap.put(ADMIN_REJECT_ORDER, new AdminRejectOrderCommand(orderService));
        commandMap.put(ADMIN_MODIFY_ORDER, new AdminModifyOrderCommand(orderService, itemService));
        commandMap.put(ADMIN_GET_INVOICE, new AdminGetInvoiceCommand(orderService, invoiceService));
        commandMap.put(ADMIN_GET_INVOICES, new AdminGetInvoicesCommand(orderService, invoiceService));
        commandMap.put(ADMIN_GET_CATEGORIES, new AdminGetCategoriesCommand(orderService, categoryService));
        commandMap.put(ADMIN_GET_ITEMS, new AdminGetItemsCommand(orderService, itemService, categoryService));
        commandMap.put(ADMIN_GET_ORDER, new AdminGetOrderCommand(orderService, orderItemService));
        commandMap.put(ADMIN_MODIFY_CATEGORY, new AdminModifyCategoryCommand(orderService, categoryService));
        commandMap.put(ADMIN_CREATE_CATEGORY, new AdminCreateNewCategoryCommand(orderService, categoryService));
        commandMap.put(ADMIN_DELETE_CATEGORY, new AdminDeleteCategoryCommand(orderService, categoryService));
        commandMap.put(ADMIN_MODIFY_ITEM, new AdminModifyItemCommand(orderService, itemService, categoryService));
        commandMap.put(ADMIN_MODIFY_ITEM_IMAGE, new AdminModifyItemImageCommand(orderService, itemService, categoryService));
        commandMap.put(ADMIN_CREATE_ITEM, new AdminCreateItemCommand(orderService, itemService, categoryService));
        commandMap.put(ADMIN_ITEM_FORM, new AdminGetItemFormCommand(orderService, categoryService));
    }

    @Override
    public Command getCommand(String cmd) {
        return ofNullable(commandMap.get(cmd))
                .orElseThrow(NoSuchElementException::new);
    }

}
