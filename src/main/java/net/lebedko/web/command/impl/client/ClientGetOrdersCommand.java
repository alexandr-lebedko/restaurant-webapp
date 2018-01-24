package net.lebedko.web.command.impl.client;

import net.lebedko.dao.paging.Page;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.user.User;
import net.lebedko.service.OrderService;
import net.lebedko.web.command.ICommand;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;

import static net.lebedko.web.util.constant.WebConstant.PAGE;

public class ClientGetOrdersCommand implements ICommand {
    private final static IResponseAction ORDERS_FORWARD = new ForwardAction(PAGE.CLIENT_ORDERS);
    private OrderService orderService;

    public ClientGetOrdersCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public IResponseAction execute(IContext context) {
        final User user = context.getSessionAttribute(User.class, Attribute.USER);
        final Page<Order> orderPage = orderService.getByUser(user, CommandUtils.parsePageable(context));

        context.addRequestAttribute(Attribute.PAGED_DATA, orderPage);
        return ORDERS_FORWARD;
    }

}
