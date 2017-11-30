package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderState;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.WebConstant;

import java.util.Collection;
import java.util.NoSuchElementException;

public class AdminGetOrdersCommand extends AbstractAdminCommand {
    private static final IResponseAction ORDERS_FORWARD = new ForwardAction(WebConstant.PAGE.ADMIN_ORDERS);

    public AdminGetOrdersCommand(OrderService orderService) {
        super(orderService);
    }

    @Override
    protected IResponseAction _doExecute(IContext context) throws ServiceException {
        final OrderState orderState = parseState(context);
        final Collection<Order> orders = orderService.getByState(orderState);

        context.addRequestAttribute(Attribute.ORDER_STATE, orderState);
        context.addRequestAttribute(Attribute.ORDERS, orders);

        return ORDERS_FORWARD;
    }

    private OrderState parseState(IContext context) {
        try {
            return OrderState.valueOf(context.getRequestParameter(Attribute.ORDER_STATE));
        } catch (IllegalArgumentException | NullPointerException e) {
            LOG.error(e);
            throw new NoSuchElementException();
        }
    }
}
