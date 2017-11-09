package net.lebedko.web.command.impl.admin;

import net.lebedko.service.InvoiceService;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.constant.Attribute;

import static net.lebedko.web.util.constant.WebConstant.*;

public class AdminGetNewOrdersCommand extends AbstractAdminCommand {
    private final static IResponseAction ORDERS_FORWARD = new ForwardAction(PAGE.ADMIN_ORDERS);

    public AdminGetNewOrdersCommand(OrderService orderService, InvoiceService invoiceService) {
        super(orderService, invoiceService);
    }

    @Override
    protected IResponseAction _doExecute(IContext context) throws ServiceException {
        context.addRequestAttribute(Attribute.ADMIN_NEW_ORDERS, orderService.getUnprocessedOrders());

        return ORDERS_FORWARD;
    }
}
