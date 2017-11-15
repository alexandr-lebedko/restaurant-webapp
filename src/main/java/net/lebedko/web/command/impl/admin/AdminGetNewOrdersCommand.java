package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.order.State;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.constant.Attribute;

import static net.lebedko.web.util.constant.WebConstant.*;

public class AdminGetNewOrdersCommand extends AbstractAdminCommand {
    private final static IResponseAction ORDERS_FORWARD = new ForwardAction(PAGE.ADMIN_ORDERS_PAGE);

    public AdminGetNewOrdersCommand(OrderService orderService, InvoiceService invoiceService) {
        super(orderService, invoiceService);
    }

    @Override
    protected IResponseAction _doExecute(IContext context) throws ServiceException {
        context.addRequestAttribute(Attribute.ORDER_STATE, State.NEW);
        context.addRequestAttribute(Attribute.ORDERS, orderService.getOrders(State.NEW));

        return ORDERS_FORWARD;
    }
}
