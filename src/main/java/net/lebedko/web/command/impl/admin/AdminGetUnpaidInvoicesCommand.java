package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.invoice.State;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.constant.Attribute;

import static net.lebedko.web.util.constant.WebConstant.*;

public class AdminGetUnpaidInvoicesCommand extends AbstractAdminCommand {
    private static final IResponseAction INVOICES_FORWARD = new ForwardAction(PAGE.ADMIN_INVOICES);

    public AdminGetUnpaidInvoicesCommand(OrderService orderService, InvoiceService invoiceService) {
        super(orderService, invoiceService);
    }

    @Override
    protected IResponseAction _doExecute(IContext context) throws ServiceException {
        context.addRequestAttribute(Attribute.INVOICE_STATE, State.UNPAID);
        context.addRequestAttribute(Attribute.INVOICES, invoiceService.getByState(State.UNPAID));

        return INVOICES_FORWARD;
    }
}
