package net.lebedko.web.command.impl.client;

import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.user.User;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.OrderItemService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.PageErrorNames;
import net.lebedko.web.util.constant.URL;
import net.lebedko.web.validator.Errors;

import java.util.Collection;

import static net.lebedko.web.util.constant.WebConstant.*;

public class ClientPayInvoiceCommand extends AbstractCommand {
    private InvoiceService invoiceService;
    private OrderItemService orderItemService;

    public ClientPayInvoiceCommand(InvoiceService invoiceService, OrderItemService orderItemService) {
        this.invoiceService = invoiceService;
        this.orderItemService = orderItemService;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        Errors errors = new Errors();

        Long invoiceId = CommandUtils.parseToLong(context.getRequestParameter(Attribute.INVOICE_ID), -1L);
        User user = context.getSessionAttribute(User.class, Attribute.USER);

        try {
            invoiceService.payInvoice(invoiceId, user);
            return new RedirectAction(URL.CLIENT_INVOICE.concat("?").concat(Attribute.INVOICE_ID).concat("=").concat(invoiceId.toString()));
        } catch (IllegalStateException e) {
            errors.register("invoiceError", PageErrorNames.INVOICE_PAY_ERROR);
        }


        Invoice invoice = invoiceService.getInvoice(invoiceId, user);
        Collection<OrderItem> orderItems = orderItemService.getOrderItems(invoice);

        context.addErrors(errors);
        context.addRequestAttribute(Attribute.INVOICE, invoice);
        context.addRequestAttribute(Attribute.ORDER_ITEMS, orderItems);

        return new ForwardAction(PAGE.CLIENT_INVOICE);
    }
}
