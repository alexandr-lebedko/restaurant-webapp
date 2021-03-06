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
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.QueryBuilder;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.PageErrorNames;
import net.lebedko.web.util.constant.URL;
import net.lebedko.web.util.constant.WebConstant;
import net.lebedko.web.validator.Errors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

public class ClientPayInvoiceCommand implements Command {
    private static final Logger LOG = LogManager.getLogger();
    private static final ResponseAction INVOICE_FORWARD = new ForwardAction(WebConstant.PAGE.CLIENT_INVOICE);

    private InvoiceService invoiceService;
    private OrderItemService orderItemService;

    public ClientPayInvoiceCommand(InvoiceService invoiceService, OrderItemService orderItemService) {
        this.invoiceService = invoiceService;
        this.orderItemService = orderItemService;
    }

    @Override
    public ResponseAction execute(Context context) {
        final Long id = CommandUtils.parseToLong(context.getRequestParameter(Attribute.INVOICE_ID), -1L);
        final User user = context.getSessionAttribute(User.class, Attribute.USER);

        try {
            invoiceService.payInvoice(id, user);
            return new RedirectAction(
                    QueryBuilder.base(URL.CLIENT_INVOICE)
                            .addParam(Attribute.INVOICE_ID, id.toString())
                            .build());

        } catch (IllegalStateException e) {
            final Errors errors = new Errors();
            errors.register("invoiceHasUnprocessedOrders", PageErrorNames.INVOICE_PAY_ERROR);
            context.addErrors(errors);
            LOG.error(e);
        }

        final Invoice invoice = invoiceService.getInvoice(id, user);
        final Collection<OrderItem> orderItems = orderItemService.getOrderItems(invoice);

        context.addRequestAttribute(Attribute.INVOICE, invoice);
        context.addRequestAttribute(Attribute.ORDER_ITEMS, orderItems);
        return INVOICE_FORWARD;
    }
}
