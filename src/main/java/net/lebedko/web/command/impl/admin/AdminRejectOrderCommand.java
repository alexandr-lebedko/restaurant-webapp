package net.lebedko.web.command.impl.admin;

import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.URL;

public class AdminRejectOrderCommand extends AbstractAdminCommand {
    private final static String URL_TEMPLATE = URL.ADMIN_ORDER.concat("?").concat(Attribute.ORDER_ID).concat("=");

    public AdminRejectOrderCommand(OrderService orderService) {
        super(orderService);
    }

    @Override
    protected IResponseAction _doExecute(IContext context) throws ServiceException {
        final Long id = CommandUtils.parseToLong(context.getRequestParameter(Attribute.ORDER_ID));

        orderService.reject(id);
        return new RedirectAction(URL_TEMPLATE.concat(id.toString()));
    }
}
