package net.lebedko.web.command.impl.client;

import net.lebedko.entity.user.User;
import net.lebedko.service.OrderService;
import net.lebedko.web.command.Command;
import net.lebedko.web.command.Context;
import net.lebedko.web.response.ResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.URL;

public class ClientDeletedOrderCommand implements Command {
    private static final ResponseAction ORDERS_REDIRECT = new RedirectAction(URL.CLIENT_ORDERS);
    private OrderService orderService;

    public ClientDeletedOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public ResponseAction execute(Context context) {
        final User user = context.getSessionAttribute(User.class, Attribute.USER);
        final Long id = CommandUtils.parseToLong(context.getRequestParameter(Attribute.ORDER_ID));

        orderService.delete(id, user);
        return ORDERS_REDIRECT;
    }
}
