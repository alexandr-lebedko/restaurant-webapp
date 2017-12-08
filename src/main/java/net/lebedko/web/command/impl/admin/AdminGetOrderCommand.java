package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.service.OrderItemService;
import net.lebedko.service.OrderService;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.WebConstant;

import java.util.Collection;

public class AdminGetOrderCommand extends AbstractAdminCommand {
    private static final IResponseAction ORDER_FORWARD = new ForwardAction(WebConstant.PAGE.ADMIN_ORDER);
    private OrderItemService orderItemService;

    public AdminGetOrderCommand(OrderService orderService, OrderItemService orderItemService) {
        super(orderService);
        this.orderItemService = orderItemService;
    }

    @Override
    protected IResponseAction _doExecute(IContext context) {
        final Long orderId = CommandUtils.parseToLong(context.getRequestParameter(Attribute.ORDER_ID));
        final Order order = orderService.getById(orderId);
        final Collection<OrderItem> orderItems = orderItemService.getOrderItems(order);

        context.addRequestAttribute(Attribute.ORDER, order);
        context.addRequestAttribute(Attribute.ORDER_ITEMS, orderItems);
        return ORDER_FORWARD;
    }
}
