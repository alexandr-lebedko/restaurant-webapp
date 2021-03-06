package net.lebedko.web.command.impl.admin;

import net.lebedko.dao.paging.Page;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.invoice.InvoiceState;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.OrderService;
import net.lebedko.web.command.Context;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.ResponseAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.NoSuchElementException;

import static net.lebedko.web.util.constant.WebConstant.PAGE;

public class AdminGetInvoicesCommand extends AbstractAdminCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final ResponseAction INVOICES_FORWARD = new ForwardAction(PAGE.ADMIN_INVOICES);
    private InvoiceService invoiceService;

    public AdminGetInvoicesCommand(OrderService orderService, InvoiceService invoiceService) {
        super(orderService);
        this.invoiceService = invoiceService;
    }

    @Override
    protected ResponseAction doExecute(Context context){
        final InvoiceState state = parseState(context);
        final Page<Invoice> invoicesPage = invoiceService.getByState(state, CommandUtils.parsePageable(context));

        context.addRequestAttribute(Attribute.INVOICE_STATE, state);
        context.addRequestAttribute(Attribute.PAGED_DATA, invoicesPage);

        return INVOICES_FORWARD;
    }

    private InvoiceState parseState(Context context) {
        try {
            return InvoiceState.valueOf(context.getRequestParameter(Attribute.INVOICE_STATE));
        } catch (IllegalArgumentException | NullPointerException e) {
            LOG.error(e);
            throw new NoSuchElementException();
        }
    }
}