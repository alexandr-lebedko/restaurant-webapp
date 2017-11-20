package net.lebedko.web.command.impl.client;

import net.lebedko.entity.user.User;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.constant.Attribute;

import static net.lebedko.web.util.constant.WebConstant.*;

public class ClientGetOrdersCommand extends AbstractCommand {
    private final static IResponseAction ORDERS_FORWARD = new ForwardAction(PAGE.CLIENT_ORDERS);

    private OrderService orderService;

    public ClientGetOrdersCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        final User user = context.getSessionAttribute(User.class, Attribute.USER);

        context.addRequestAttribute(Attribute.ORDERS, orderService.getOrders(user));

        return ORDERS_FORWARD;
    }

}
