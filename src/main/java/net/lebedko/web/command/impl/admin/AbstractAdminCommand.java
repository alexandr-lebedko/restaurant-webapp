package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderState;
import net.lebedko.service.OrderService;
import net.lebedko.web.command.Command;
import net.lebedko.web.command.Context;
import net.lebedko.web.response.ResponseAction;
import net.lebedko.web.util.constant.Attribute;

import java.util.Collection;

public abstract class AbstractAdminCommand implements Command {
    protected OrderService orderService;

    public AbstractAdminCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public final ResponseAction execute(Context context) {
        ResponseAction responseAction = doExecute(context);

        addNewOrdersNumber(context);

        return responseAction;
    }

    private void addNewOrdersNumber(Context context) {
        Collection<Order> unprocessedOrders = orderService.getByState(OrderState.NEW);
        if (!unprocessedOrders.isEmpty()) {
            context.addRequestAttribute(Attribute.ORDERS_NUM, unprocessedOrders.size());
        }
    }

    protected abstract ResponseAction doExecute(Context context);
}
