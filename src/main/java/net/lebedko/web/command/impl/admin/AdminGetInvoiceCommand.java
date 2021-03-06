package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.Order;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.OrderService;
import net.lebedko.web.command.Context;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.ResponseAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;

import java.util.Collection;

import static net.lebedko.web.util.constant.WebConstant.PAGE;

public class AdminGetInvoiceCommand extends AbstractAdminCommand {
    private static final ResponseAction INVOICE_FORWARD = new ForwardAction(PAGE.ADMIN_INVOICE);
    private InvoiceService invoiceService;

    public AdminGetInvoiceCommand(OrderService orderService, InvoiceService invoiceService) {
        super(orderService);
        this.invoiceService = invoiceService;
    }

    @Override
    protected ResponseAction doExecute(Context context){
        final Long id = CommandUtils.parseToLong(context.getRequestParameter(Attribute.INVOICE_ID));
        final Invoice invoice = invoiceService.getInvoice(id);
        final Collection<Order> orders = orderService.getByInvoice(invoice);

        context.addRequestAttribute(Attribute.INVOICE, invoice);
        context.addRequestAttribute(Attribute.ORDERS, orders);
        return INVOICE_FORWARD;
    }
}