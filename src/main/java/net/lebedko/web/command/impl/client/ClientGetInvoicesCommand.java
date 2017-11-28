package net.lebedko.web.command.impl.client;

import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.user.User;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.constant.Attribute;

import java.util.Collection;

import static net.lebedko.web.util.constant.WebConstant.PAGE;

public class ClientGetInvoicesCommand extends AbstractCommand {
    private static final IResponseAction INVOICES_FORWARD = new ForwardAction(PAGE.CLIENT_INVOICES);
    private InvoiceService invoiceService;

    public ClientGetInvoicesCommand(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        final User user = context.getSessionAttribute(User.class, Attribute.USER);
        final Collection<Invoice> invoices = invoiceService.getInvoices(user);

        context.addRequestAttribute(Attribute.INVOICES, invoices);
        return INVOICES_FORWARD;
    }
}
