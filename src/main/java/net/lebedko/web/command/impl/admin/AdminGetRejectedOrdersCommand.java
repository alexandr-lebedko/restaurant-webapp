package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.order.OrderState;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.WebConstant;

public class AdminGetRejectedOrdersCommand  extends AbstractAdminCommand{
    private static final IResponseAction ORDERS_FORWARD = new ForwardAction(WebConstant.PAGE.ADMIN_ORDERS_PAGE);

    public AdminGetRejectedOrdersCommand(OrderService orderService, InvoiceService invoiceService) {
        super(orderService, invoiceService);
    }

    @Override
    protected IResponseAction _doExecute(IContext context) throws ServiceException {
        context.addRequestAttribute(Attribute.ORDER_STATE, OrderState.REJECTED);
        context.addRequestAttribute(Attribute.ORDERS, orderService.getByState(OrderState.REJECTED));
        return ORDERS_FORWARD;
    }
}
