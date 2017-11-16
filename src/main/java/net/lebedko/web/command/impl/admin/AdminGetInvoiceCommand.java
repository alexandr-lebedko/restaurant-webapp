package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;

import java.util.Collection;

import static java.util.Objects.nonNull;
import static net.lebedko.web.util.constant.WebConstant.*;

public class AdminGetInvoiceCommand extends AbstractAdminCommand {
    private static final IResponseAction INVOICE_FORWARD = new ForwardAction(PAGE.ADMIN_INVOICE);

    public AdminGetInvoiceCommand(OrderService orderService, InvoiceService invoiceService) {
        super(orderService, invoiceService);
    }

    @Override
    protected IResponseAction _doExecute(IContext context) throws ServiceException {
        final Invoice invoice = invoiceService.getInvoice(getInvoiceId(context));
        if (nonNull(invoice)) {
            final Collection<OrderItem> orderItems = orderService.getOrderItemsByInvoice(invoice);
            context.addRequestAttribute(Attribute.INVOICE, invoice);
            context.addRequestAttribute(Attribute.ORDER_ITEMS, orderItems);
        }

        return INVOICE_FORWARD;
    }

    private Long getInvoiceId(IContext context) {
        return CommandUtils.parseToLong(context.getRequestParameter(Attribute.INVOICE_ID), -1L);
    }
}
