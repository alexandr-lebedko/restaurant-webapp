package net.lebedko.web.command.impl;

import net.lebedko.service.*;
import net.lebedko.web.command.ICommand;
import net.lebedko.web.command.ICommandFactory;
import net.lebedko.web.command.impl.admin.*;
import net.lebedko.web.command.impl.auth.*;
import net.lebedko.web.command.impl.client.*;
import net.lebedko.web.validator.item.*;
import net.lebedko.web.validator.ImageValidator;
import net.lebedko.web.validator.user.UserValidator;

import java.util.HashMap;
import java.util.Map;

import static net.lebedko.service.ServiceFactory.*;
import static net.lebedko.web.util.constant.Commands.*;
import static net.lebedko.web.util.constant.WebConstant.COMMAND.*;
import static net.lebedko.web.util.constant.WebConstant.COMMAND.GET_ADMIN_MENU;
import static net.lebedko.web.util.constant.WebConstant.COMMAND.GET_ADMIN_NEW_ITEM;
import static net.lebedko.web.util.constant.WebConstant.COMMAND.GET_SIGN_UP;
import static net.lebedko.web.util.constant.WebConstant.COMMAND.POST_ADMIN_NEW_ITEM;
import static net.lebedko.web.util.constant.WebConstant.COMMAND.POST_SIGN_UP;

/**
 * alexandr.lebedko : 12.06.2017
 */
public class CommandFactoryImpl implements ICommandFactory {
    private Map<String, ICommand> commandMap = new HashMap<>();

    public CommandFactoryImpl() {
        final UserService userService= getService(UserService.class);
        final OrderService orderService = getService(OrderService.class);
        final InvoiceService invoiceService = getService(InvoiceService.class);
        final  OrderItemService orderItemService = getService(OrderItemService.class);
        final ItemService itemService = getService(ItemService.class);
        final CategoryService categoryService = getService(CategoryService.class);

        commandMap.put(GET_SIGN_IN, new SignInGetCommand());
        commandMap.put(GET_SIGN_UP, new SignUpGetCommand());
        commandMap.put(SIGN_OUT, new SignOutCommand());
        commandMap.put(POST_SIGN_IN, new SignInPostCommand(userService));
        commandMap.put(POST_SIGN_UP, new SignUpPostCommand(userService, new UserValidator()));

        commandMap.put(CLIENT_GET_ORDER, new ClientGetOrderCommand(orderService));
        commandMap.put(CLIENT_GET_ORDERS, new ClientGetOrdersCommand(orderService));
        commandMap.put(CLIENT_CREATE_ORDER, new ClientCreateOrderCommand(orderService));
        commandMap.put(CLIENT_GET_ORDER_FORM, new OrderGetCommand());
        commandMap.put(CLIENT_GET_INVOICE, new ClientGetInvoiceCommand(invoiceService, orderItemService));
        commandMap.put(CLIENT_GET_INVOICES, new ClientGetInvoicesCommand(invoiceService));
        commandMap.put(CLIENT_CLOSE_INVOICE, new ClientCloseInvoiceCommand(invoiceService, orderItemService));
        commandMap.put(CLIENT_ADD_ITEM_TO_BUCKET, new ClientAddItemToOrderCommand(itemService));
        commandMap.put(CLIENT_CLEAR_ORDER_BUCKET, new ClientDeleteBucketCommand());
        commandMap.put(CLIENT_GET_MENU, new ClientGetMenuCommand(categoryService,itemService));
        commandMap.put(CLIENT_REJECT_ORDER, new ClientRejectModifiedOrderCommand(orderService));
        commandMap.put(CLIENT_MODIFY_ORDER, new ClientModifyOrderCommand(orderService));
        commandMap.put(CLIENT_SUBMIT_MODIFIED_ORDER, new ClientSubmitModifiedOrderCommand(orderService));

        commandMap.put(GET_ADMIN_NEW_ORDERS, new AdminGetNewOrdersCommand(orderService, invoiceService));
        commandMap.put(GET_ADMIN_ORDER_DETAILS, new AdminGetOrderDetailsCommand(orderService, invoiceService));
        commandMap.put(ADMIN_PROCESS_ORDER, new AdminProcessOrderCommand(orderService, invoiceService));
        commandMap.put(ADMIN_REJECT_ORDER, new AdminRejectOrderCommand(orderService, invoiceService));
        commandMap.put(ADMIN_MODIFY_ORDER, new AdminModifyOrderCommand(orderService, invoiceService));
        commandMap.put(ADMIN_GET_PROCESSED_ORDERS, new AdminGetProcessedOrdersCommand(orderService, invoiceService));
        commandMap.put(ADMIN_GET_REJECTED_ORDERS, new AdminGetRejectedOrdersCommand(orderService, invoiceService));
        commandMap.put(ADMIN_GET_MODIFIED_ORDERS, new AdminGetModifiedOrdersCommand(orderService, invoiceService));
        commandMap.put(ADMIN_GET_CLOSED_INVOICES, new AdminGetClosedInvoicesCommand(orderService, invoiceService));
        commandMap.put(ADMIN_GET_PAID_INVOICES, new AdminGetPaidInvoicesCommand(orderService, invoiceService));
        commandMap.put(ADMIN_GET_UNPAID_INVOICES, new AdminGetUnpaidInvoicesCommand(orderService, invoiceService));
        commandMap.put(ADMIN_GET_ACTIVE_INVOICES, new AdminGetActiveInvoicesCommand(orderService, invoiceService));
        commandMap.put(ADMIN_GET_INVOICE, new AdminGetInvoiceCommand(orderService, invoiceService));

        commandMap.put(GET_ADMIN_MAIN, new AdminMainGetCommand(getService(OrderService.class), getService(InvoiceService.class)));
        commandMap.put(GET_NEW_DISH, new NewDishGetCommand());

        commandMap.put(GET_NEW_CATEGORY, new NewCategoryGetCommand());

        commandMap.put(POST_NEW_CATEGORY, new NewCategoryPostCommand(
                getService(CategoryService.class),
                new CategoryValidator(),
                getService(FileService.class),
                new ImageValidator()));

        commandMap.put(GET_ADMIN_MENU, new AdminMenuGetCommand(getService(CategoryService.class)));

        commandMap.put(GET_ADMIN_NEW_ITEM, new NewItemGetCommand(getService(CategoryService.class)));
        commandMap.put(POST_ADMIN_NEW_ITEM, new NewItemPostCommand(
                new ImageValidator(),
                new MenuItemValidator(),
                getService(CategoryService.class),
                getService(ItemService.class)));
    }

    @Override
    public ICommand getCommand(String cmd) {
        return commandMap.get(cmd);
    }

}
