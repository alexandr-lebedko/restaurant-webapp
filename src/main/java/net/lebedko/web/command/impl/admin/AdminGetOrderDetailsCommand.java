package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.service.InvoiceService;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.constant.Attribute;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;

import static net.lebedko.web.util.constant.Attribute.ORDER_DETAILS;
import static net.lebedko.web.util.constant.WebConstant.*;

public class AdminGetOrderDetailsCommand extends AbstractAdminCommand {
    private static final IResponseAction ORDER_DETAILS_FORWARD = new ForwardAction(PAGE.ADMIN_ORDER_DETAILS);

    public AdminGetOrderDetailsCommand(OrderService orderService, InvoiceService invoiceService) {
        super(orderService, invoiceService);
    }

    @Override
    protected IResponseAction _doExecute(IContext context) throws ServiceException {
        context.addRequestAttribute(ORDER_DETAILS, getOrderDetails(context));

        return ORDER_DETAILS_FORWARD;
    }

    private Pair<Order, Collection<OrderItem>> getOrderDetails(IContext context) {
        return orderService.getOrderAndOrderItemsByOrderId(getOrderId(context));
    }

    private Long getOrderId(IContext context) {
        Long id = -1L;

        try {
            id = Long.valueOf(context.getRequestParameter(Attribute.ORDER_ID));
        } catch (NumberFormatException nfe) {
            LOG.error(nfe);
        }
        return id;
    }

}
