package net.lebedko.web.command.impl.client;

import net.lebedko.entity.user.User;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;

import static net.lebedko.web.util.constant.WebConstant.*;

public class InvoicesGetCommand extends AbstractCommand {
    private static final IResponseAction INVOICES_FORWARD = new ForwardAction(PAGE.CLIENT_INVOICES);

    private InvoiceService invoiceService;

    public InvoicesGetCommand(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {

        context.addRequestAttribute("currentInvoice", invoiceService.getCurrentInvoiceAndContent(getUser(context)));
        return INVOICES_FORWARD;
    }

    private User getUser(IContext context) {
        return context.getSessionAttribute(User.class, "user");
    }
}
