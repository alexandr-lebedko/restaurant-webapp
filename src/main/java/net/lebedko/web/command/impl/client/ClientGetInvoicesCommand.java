package net.lebedko.web.command.impl.client;

import net.lebedko.dao.paging.Page;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.user.User;
import net.lebedko.service.InvoiceService;
import net.lebedko.web.command.Command;
import net.lebedko.web.command.Context;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.ResponseAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;

import static net.lebedko.web.util.constant.WebConstant.PAGE;

public class ClientGetInvoicesCommand implements Command {
    private static final ResponseAction INVOICES_FORWARD = new ForwardAction(PAGE.CLIENT_INVOICES);
    private InvoiceService invoiceService;

    public ClientGetInvoicesCommand(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    public ResponseAction execute(Context context) {
        final User user = context.getSessionAttribute(User.class, Attribute.USER);
        final Page<Invoice> invoicesPage = invoiceService.getInvoices(user, CommandUtils.parsePageable(context));

        context.addRequestAttribute(Attribute.PAGED_DATA, invoicesPage);
        return INVOICES_FORWARD;
    }
}
