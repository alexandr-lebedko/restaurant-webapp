package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderState;
import net.lebedko.service.OrderService;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.constant.Attribute;

import java.util.Collection;

public abstract class AbstractAdminCommand extends AbstractCommand {
    protected OrderService orderService;

    public AbstractAdminCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    protected final IResponseAction doExecute(IContext context) {
        IResponseAction responseAction = _doExecute(context);

        addNewOrdersNumber(context);

        return responseAction;
    }

    private void addNewOrdersNumber(IContext context) {
        Collection<Order> unprocessedOrders = orderService.getByState(OrderState.NEW);
        if (!unprocessedOrders.isEmpty()) {
            context.addRequestAttribute(Attribute.ORDERS_NUM, unprocessedOrders.size());
        }
    }


    protected abstract IResponseAction _doExecute(IContext context);
}
