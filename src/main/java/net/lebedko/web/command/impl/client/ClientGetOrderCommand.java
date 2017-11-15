package net.lebedko.web.command.impl.client;

import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.user.User;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.WebConstant;
import net.lebedko.web.util.constant.WebConstant.PAGE;

import java.util.Collection;

import static java.util.Objects.nonNull;

public class ClientGetOrderCommand extends AbstractCommand {
    private static final IResponseAction ORDER_FORWARD = new ForwardAction(PAGE.CLIENT_ORDER);

    private OrderService orderService;

    public ClientGetOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        Order order = orderService.getOrder(getOrderId(context), getUser(context));
        if (nonNull(order)) {
            Collection<OrderItem> orderItems = orderService.getOrderItems(order);
            context.addRequestAttribute(Attribute.ORDER, order);
            context.addRequestAttribute(Attribute.ORDER_ITEMS, orderItems);
        }

        return ORDER_FORWARD;
    }

    private Long getOrderId(IContext context) {
        return CommandUtils.parseToLong(context.getRequestParameter(Attribute.ORDER_ID), -1L);
    }

    private User getUser(IContext context) {
        return context.getSessionAttribute(User.class, Attribute.USER);
    }
}
