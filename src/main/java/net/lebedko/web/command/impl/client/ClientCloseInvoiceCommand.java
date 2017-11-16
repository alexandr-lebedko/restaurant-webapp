package net.lebedko.web.command.impl.client;

import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.user.User;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.OrderItemService;
import net.lebedko.service.exception.NoSuchEntityException;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.service.exception.UnprocessedOrdersException;
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

import static java.lang.Long.parseLong;
import static net.lebedko.web.util.constant.WebConstant.*;

public class CloseInvoiceCommand extends AbstractCommand {
    private static final IResponseAction INVOICE_FORWARD = new ForwardAction(PAGE.CLIENT_INVOICE);

    private InvoiceService invoiceService;
    private OrderItemService orderItemService;

    public CloseInvoiceCommand(InvoiceService invoiceService, OrderItemService orderItemService) {
        this.invoiceService = invoiceService;
        this.orderItemService = orderItemService;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        Errors errors = new Errors();

        Long invoiceId = CommandUtils.parseToLong(context.getRequestParameter(Attribute.INVOICE_ID), -1L);
        User user = context.getSessionAttribute(User.class, Attribute.USER);

        try {
            invoiceService.closeInvoice(invoiceId, user);
            return new RedirectAction(URL.CLIENT_INVOICE.concat("?").concat(Attribute.INVOICE_ID).concat("=").concat(invoiceId.toString()));
        } catch (IllegalStateException e) {
            errors.register("invoiceState", PageErrorNames.INVOICE_INVALID_STATE);
        }


        Invoice invoice = invoiceService.getInvoice(invoiceId, user);
        Collection<OrderItem> orderItems = orderItemService.getOrderItems(invoice);

        context.addRequestAttribute(Attribute.INVOICE, invoice);
        context.addRequestAttribute(Attribute.ORDER_ITEMS, orderItems);

        context.addErrors(errors);
        context.addRequestAttribute(Attribute.INVOICE, invoiceService.getInvoice(invoiceId));
        context.addRequestAttribute(Attribute.ORDER_ITEMS, orderItems);

        return INVOICE_FORWARD;
    }
}
