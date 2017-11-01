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
import net.lebedko.web.util.constant.WebConstant;

import java.util.Collection;

public class OrdersGetCommand extends AbstractCommand {
    private static final IResponseAction ORDER_INFO_FORWARD = new ForwardAction(WebConstant.PAGE.CLIENT_ORDERS);

    private OrderService orderService;

    public OrdersGetCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        context.addRequestAttribute("orderItems", getOrderItems(context));
        context.addRequestAttribute("orders", getOrders(context));

        return ORDER_INFO_FORWARD;
    }

    private Collection<Order> getOrders(IContext context) {
        return orderService.getOrdersByUser(getUser(context));
    }

    private Collection<OrderItem> getOrderItems(IContext context) {
        return orderService.getByOrderIdAndUser(getOrderId(context), getUser(context));
    }

    private User getUser(IContext context) {
        return context.getSessionAttribute(User.class, "user");
    }

    private Long getOrderId(IContext context) {
        Long id = -1L;
        try {
            id = Long.valueOf(context.getRequestParameter("id"));
        } catch (NumberFormatException nfe) {
            LOG.error(nfe);
        }
        return id;
    }
}
