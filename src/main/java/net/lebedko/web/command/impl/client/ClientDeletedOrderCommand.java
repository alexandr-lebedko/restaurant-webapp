package net.lebedko.web.command.impl.client;

import net.lebedko.entity.user.User;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.URL;

public class ClientDeletedOrderCommand extends AbstractCommand {
    private static final IResponseAction ORDERS_REDIRECT = new RedirectAction(URL.CLIENT_ORDERS);
    private OrderService orderService;

    public ClientDeletedOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        final User user = context.getSessionAttribute(User.class, Attribute.USER);
        final Long orderId = CommandUtils.parseToLong(context.getRequestParameter(Attribute.ORDER_ID));

        orderService.delete(orderId, user);
        return ORDERS_REDIRECT;
    }
}
