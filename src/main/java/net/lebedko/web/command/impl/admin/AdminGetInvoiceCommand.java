package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.item.Item;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;
import static net.lebedko.web.util.constant.WebConstant.*;

public class AdminGetInvoiceCommand extends AbstractAdminCommand {
    private static final IResponseAction INVOICE_FORWARD = new ForwardAction(PAGE.ADMIN_INVOICE);
    private static final Comparator<Order> COMPARATOR = Comparator.comparing(Order::getCreatedOn).reversed();


    public AdminGetInvoiceCommand(OrderService orderService, InvoiceService invoiceService) {
        super(orderService, invoiceService);
    }

    @Override
    protected IResponseAction _doExecute(IContext context) throws ServiceException {
        final Invoice invoice = invoiceService.getInvoice(getInvoiceId(context));
        final Collection<OrderItem> orderItems = orderService.getOrderItemsByInvoice(invoice);

        final Map<Order, List<OrderItem>> itemsToOrder = new TreeMap<>(COMPARATOR);

        itemsToOrder.putAll(orderItems.stream()
                .collect(Collectors.groupingBy(OrderItem::getOrder)));

        context.addRequestAttribute(Attribute.INVOICE, invoice);
        context.addRequestAttribute(Attribute.ITEMS_TO_ORDER, itemsToOrder);

        return INVOICE_FORWARD;
    }

    private Long getInvoiceId(IContext context) {
        return CommandUtils.parseToLong(context.getRequestParameter(Attribute.INVOICE_ID), -1L);
    }
}
