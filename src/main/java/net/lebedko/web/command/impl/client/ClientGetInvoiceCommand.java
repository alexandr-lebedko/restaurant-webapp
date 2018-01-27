package net.lebedko.web.command.impl.client;

import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.user.User;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.OrderItemService;
import net.lebedko.web.command.Command;
import net.lebedko.web.command.Context;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.ResponseAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;

import java.util.Collection;

import static net.lebedko.web.util.constant.WebConstant.PAGE;

public class ClientGetInvoiceCommand implements Command {
    private ResponseAction INVOICE_FORWARD = new ForwardAction(PAGE.CLIENT_INVOICE);

    private InvoiceService invoiceService;
    private OrderItemService orderItemService;

    public ClientGetInvoiceCommand(InvoiceService invoiceService, OrderItemService orderItemService) {
        this.invoiceService = invoiceService;
        this.orderItemService = orderItemService;
    }

    @Override
    public ResponseAction execute(Context context) {
        final Long id = CommandUtils.parseToLong(context.getRequestParameter(Attribute.INVOICE_ID));
        final User user = context.getSessionAttribute(User.class, Attribute.USER);

        final Invoice invoice = invoiceService.getInvoice(id, user);
        final Collection<OrderItem> orderItems = orderItemService.getOrderItems(invoice);

        context.addRequestAttribute(Attribute.INVOICE, invoice);
        context.addRequestAttribute(Attribute.ORDER_ITEMS, orderItems);

        return INVOICE_FORWARD;
    }
}
