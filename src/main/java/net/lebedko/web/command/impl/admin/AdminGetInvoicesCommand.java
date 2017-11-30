package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.invoice.InvoiceState;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.constant.Attribute;

import java.util.Collection;
import java.util.NoSuchElementException;

import static net.lebedko.web.util.constant.WebConstant.PAGE;

public class AdminGetInvoicesCommand extends AbstractAdminCommand {
    private static final IResponseAction INVOICES_FORWARD = new ForwardAction(PAGE.ADMIN_INVOICES);
    private InvoiceService invoiceService;

    public AdminGetInvoicesCommand(OrderService orderService, InvoiceService invoiceService) {
        super(orderService);
        this.invoiceService = invoiceService;
    }

    @Override
    protected IResponseAction _doExecute(IContext context) throws ServiceException {
        final InvoiceState state = parseState(context);
        final Collection<Invoice> invoices = invoiceService.getByState(state);

        context.addRequestAttribute(Attribute.INVOICE_STATE, state);
        context.addRequestAttribute(Attribute.INVOICES, invoices);

        return INVOICES_FORWARD;
    }

    private InvoiceState parseState(IContext context) {
        try {
            return InvoiceState.valueOf(context.getRequestParameter(Attribute.INVOICE_STATE));
        } catch (IllegalArgumentException | NullPointerException e) {
            LOG.error(e);
            throw new NoSuchElementException();
        }
    }
}
