package net.lebedko.web.command.impl.admin;

import net.lebedko.dao.paging.Page;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderState;
import net.lebedko.service.OrderService;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.WebConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.NoSuchElementException;

public class AdminGetOrdersCommand extends AbstractAdminCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final IResponseAction ORDERS_FORWARD = new ForwardAction(WebConstant.PAGE.ADMIN_ORDERS);

    public AdminGetOrdersCommand(OrderService orderService) {
        super(orderService);
    }

    @Override
    protected IResponseAction doExecute(IContext context) {
        final OrderState orderState = parseState(context);
        final Page<Order> ordersPage = orderService.getByState(orderState, CommandUtils.parsePageable(context));

        context.addRequestAttribute(Attribute.ORDER_STATE, orderState);
        context.addRequestAttribute(Attribute.PAGED_DATA, ordersPage);

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
