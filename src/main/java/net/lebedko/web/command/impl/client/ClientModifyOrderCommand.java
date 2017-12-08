package net.lebedko.web.command.impl.client;

import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.user.User;
import net.lebedko.service.OrderBucket;
import net.lebedko.service.OrderItemService;
import net.lebedko.service.OrderService;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.URL;

import java.util.Collection;

public class ClientModifyOrderCommand extends AbstractCommand {
    private static final IResponseAction CLIENT_ORDER_REDIRECT = new RedirectAction(URL.CLIENT_ORDER_FORM);

    private OrderService orderService;
    private OrderItemService orderItemService;

    public ClientModifyOrderCommand(OrderService orderService, OrderItemService orderItemService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @Override
    protected IResponseAction doExecute(IContext context) {
        final Long id = CommandUtils.parseToLong(context.getRequestParameter(Attribute.ORDER_ID));

        final User user = context.getSessionAttribute(User.class, Attribute.USER);
        final Order order = orderService.getById(id);
        final Collection<OrderItem> orderItems = orderItemService.getOrderItems(order);

        final OrderBucket bucket = CommandUtils.getOrderBucketFromSession(context);
        orderItems.forEach(oi -> bucket.add(oi.getItem(), oi.getQuantity()));

        orderService.delete(id, user);
        context.addSessionAttribute(Attribute.ORDER_BUCKET, bucket);

        return CLIENT_ORDER_REDIRECT;
    }
}
