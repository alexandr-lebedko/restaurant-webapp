package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.invoice.InvoiceState;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.WebConstant;

public class AdminGetPaidInvoicesCommand extends AbstractAdminCommand {
    private static final IResponseAction INVOICES_FORWARD = new ForwardAction(WebConstant.PAGE.ADMIN_INVOICES);
    private InvoiceService invoiceService;

    public AdminGetPaidInvoicesCommand(OrderService orderService, InvoiceService invoiceService) {
        super(orderService);
        this.invoiceService = invoiceService;
    }

    @Override
    protected IResponseAction _doExecute(IContext context) throws ServiceException {
        context.addRequestAttribute(Attribute.INVOICE_STATE, InvoiceState.PAID);
        context.addRequestAttribute(Attribute.INVOICES, invoiceService.getByState(InvoiceState.PAID));
        return INVOICES_FORWARD;
    }
}
