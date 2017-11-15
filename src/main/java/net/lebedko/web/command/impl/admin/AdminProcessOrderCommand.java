package net.lebedko.web.command.impl.admin;

import net.lebedko.service.InvoiceService;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.URL;

public class AdminProcessOrderCommand extends AbstractAdminCommand {


    public AdminProcessOrderCommand(OrderService orderService, InvoiceService invoiceService) {
        super(orderService, invoiceService);
    }

    @Override
    protected IResponseAction _doExecute(IContext context) throws ServiceException {
        Long orderId = getOrderId(context);
        orderService.processOrder(orderId);

        return new RedirectAction(URL.ADMIN_ORDER_DETAILS.concat("?").concat(Attribute.ORDER_ID).concat("=").concat(orderId.toString()));
    }

    private Long getOrderId(IContext context) {
        Long id = -1L;
        try {
            id = Long.valueOf(context.getRequestParameter(Attribute.ORDER_ID));
        } catch (NumberFormatException e) {
            LOG.error(e);
        }

        return id;
    }


}
