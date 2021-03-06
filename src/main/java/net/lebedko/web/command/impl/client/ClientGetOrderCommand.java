package net.lebedko.web.command.impl.client;

import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.user.User;
import net.lebedko.service.OrderItemService;
import net.lebedko.service.OrderService;
import net.lebedko.web.command.Command;
import net.lebedko.web.command.Context;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.ResponseAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.WebConstant.PAGE;

import java.util.Collection;

public class ClientGetOrderCommand implements Command {
    private static final ResponseAction ORDER_FORWARD = new ForwardAction(PAGE.CLIENT_ORDER);

    private OrderService orderService;
    private OrderItemService orderItemService;

    public ClientGetOrderCommand(OrderService orderService, OrderItemService orderItemService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @Override
    public ResponseAction execute(Context context) {
        final Long orderId =CommandUtils.parseToLong(context.getRequestParameter(Attribute.ORDER_ID));
        final User user =  context.getSessionAttribute(User.class, Attribute.USER);
        final Order order = orderService.getByUserAndId(orderId, user);
        final Collection<OrderItem> orderItems = orderItemService.getOrderItems(order);
        context.addRequestAttribute(Attribute.ORDER, order);
        context.addRequestAttribute(Attribute.ORDER_ITEMS, orderItems);
        return ORDER_FORWARD;
    }
}