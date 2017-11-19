package net.lebedko.web.command.impl.client;

import net.lebedko.entity.user.User;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.URL;

public class ClientPayInvoiceCommand extends AbstractCommand {
    private InvoiceService invoiceService;

    public ClientPayInvoiceCommand(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        final Long invoiceId = CommandUtils.parseToLong(context.getRequestParameter(Attribute.INVOICE_ID), -1L);
        final User user = context.getSessionAttribute(User.class, Attribute.USER);

        invoiceService.payInvoice(invoiceId, user);

        return new RedirectAction(URL.CLIENT_INVOICE.concat("?").concat(Attribute.INVOICE_ID).concat("=").concat(invoiceId.toString()));

    }
}
