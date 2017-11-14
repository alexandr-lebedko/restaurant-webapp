package net.lebedko.web.command.impl;

import net.lebedko.service.*;
import net.lebedko.web.command.ICommand;
import net.lebedko.web.command.ICommandFactory;
import net.lebedko.web.command.impl.admin.*;
import net.lebedko.web.command.impl.auth.*;
import net.lebedko.web.command.impl.client.*;
import net.lebedko.web.util.constant.WebConstant;
import net.lebedko.web.validator.item.*;
import net.lebedko.web.validator.ImageValidator;
import net.lebedko.web.validator.order.OrderValidator;
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
        commandMap.put(SIGN_OUT, new SignOutCommand());

        commandMap.put(GET_CLIENT_MAIN, new MainPageGetCommand(getService(CategoryService.class)));

        commandMap.put(CLIENT_GET_ITEMS, new ItemsByCategoryGetCommand(getService(CategoryService.class), getService(ItemService.class)));
        commandMap.put(ADD_ITEM_TO_BUCKET, new AddItemToOrderContentCommand(getService(ItemService.class)));

        commandMap.put(GET_CLIENT_ORDER, new OrderGetCommand());
        commandMap.put(POST_CLIENT_ORDER, new OrderPostCommand(getService(OrderService.class), new OrderValidator()));
        commandMap.put(GET_CLIENT_ORDERS, new OrdersGetCommand(getService(OrderService.class)));

        commandMap.put(GET_CLIENT_INVOICES, new InvoicesGetCommand(getService(InvoiceService.class)));
        commandMap.put(CLIENT_CLOSE_INVOICE, new CloseInvoiceCommand(getService(InvoiceService.class)));

        commandMap.put(POST_SIGN_IN, new SignInPostCommand(getService(UserService.class)));
        commandMap.put(GET_SIGN_IN, new SignInGetCommand());

        commandMap.put(GET_SIGN_UP, new SignUpGetCommand());
        commandMap.put(POST_SIGN_UP, new SignUpPostCommand(getService(UserService.class), new UserValidator()));

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

        commandMap.put(GET_ADMIN_NEW_ORDERS,
                new AdminGetNewOrdersCommand(getService(OrderService.class), getService(InvoiceService.class)));
        commandMap.put(GET_ADMIN_ORDER_DETAILS,
                new AdminGetOrderDetailsCommand(getService(OrderService.class), getService(InvoiceService.class)));
        commandMap.put(ADMIN_PROCESS_ORDER,
                new AdminProcessOrderCommand(getService(OrderService.class), getService(InvoiceService.class)));
        commandMap.put(ADMIN_REJECT_ORDER,
                new AdminRejectOrderCommand(getService(OrderService.class), getService(InvoiceService.class)));
        commandMap.put(ADMIN_MODIFY_ORDER,
                new AdminModifyOrderCommand(getService(OrderService.class), getService(InvoiceService.class)));
        commandMap.put(ADMIN_GET_PROCESSED_ORDERS,
                new AdminGetProcessedOrdersCommand(getService(OrderService.class), getService(InvoiceService.class)));
        commandMap.put(ADMIN_GET_REJECTED_ORDERS,
                new AdminGetRejectedOrdersCommand(getService(OrderService.class), getService(InvoiceService.class)));
        commandMap.put(ADMIN_GET_MODIFIED_ORDERS,
                new AdminGetModifiedOrdersCommand(getService(OrderService.class), getService(InvoiceService.class)));
        commandMap.put(ADMIN_GET_CLOSED_INVOICES,
                new AdminGetClosedInvoicesCommand(getService(OrderService.class), getService(InvoiceService.class)));
        commandMap.put(ADMIN_GET_PAID_INVOICES,
                new AdminGetPaidInvoicesCommand(getService(OrderService.class), getService(InvoiceService.class)));
        commandMap.put(ADMIN_GET_UNPAID_INVOICES,
                new AdminGetUnpaidInvoicesCommand(getService(OrderService.class), getService(InvoiceService.class)));
        commandMap.put(ADMIN_GET_ACTIVE_INVOICES,
                new AdminGetActiveInvoicesCommand(getService(OrderService.class), getService(InvoiceService.class)));

    }

    @Override
    public ICommand getCommand(String cmd) {
        return commandMap.get(cmd);
    }

}
