package net.lebedko.web.command.impl.client;

import net.lebedko.entity.user.User;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.constant.WebConstant.PAGE;

public class OrdersGetCommand extends AbstractCommand {
    private static final IResponseAction ORDERS_FORWARD = new ForwardAction(PAGE.CLIENT_ORDERS);

    private OrderService orderService;

    public OrdersGetCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        User user = getUserFromSession(context);

        context.addRequestAttribute("orders", orderService.getOrdersByUser(user));
        return ORDERS_FORWARD;
    }

    private User getUserFromSession(IContext context) {
        return context.getSessionAttribute(User.class, "user");
    }
}