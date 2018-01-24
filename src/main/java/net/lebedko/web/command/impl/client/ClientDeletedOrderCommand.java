package net.lebedko.web.command.impl.client;

import net.lebedko.entity.user.User;
import net.lebedko.service.OrderService;
import net.lebedko.web.command.ICommand;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.URL;

public class ClientDeletedOrderCommand implements ICommand {
    private static final IResponseAction ORDERS_REDIRECT = new RedirectAction(URL.CLIENT_ORDERS);
    private OrderService orderService;

    public ClientDeletedOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public IResponseAction execute(IContext context) {
        final User user = context.getSessionAttribute(User.class, Attribute.USER);
        final Long id = CommandUtils.parseToLong(context.getRequestParameter(Attribute.ORDER_ID));

        orderService.delete(id, user);
        return ORDERS_REDIRECT;
    }
}
