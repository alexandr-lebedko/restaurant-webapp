package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.OrderItemService;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;

import static net.lebedko.web.util.constant.WebConstant.*;

public class AdminGetOrderCommandCommand extends AbstractAdminCommand {
    private static final IResponseAction ORDER_DETAILS_FORWARD = new ForwardAction(PAGE.ADMIN_ORDER_DETAILS);

    private OrderItemService orderItemService;

    public AdminGetOrderCommandCommand(
            OrderService orderService,
            OrderItemService orderItemService,
            InvoiceService invoiceService) {
        super(orderService, invoiceService);
    }

    @Override
    protected IResponseAction _doExecute(IContext context) throws ServiceException {
        final Long orderId = CommandUtils.parseToLong(context.getRequestParameter(Attribute.ORDER_ID), -1L);
        final Order order = orderService.getOrder(orderId);
        final Collection<OrderItem> orderItems = orderItemService.getOrderItems(order);

        context.addRequestAttribute(Attribute.ORDER_ID, order);
        context.addRequestAttribute(Attribute.ORDER_ITEMS, orderItems);

        return ORDER_DETAILS_FORWARD;
    }
}
