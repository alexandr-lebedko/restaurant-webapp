package net.lebedko.web.command.impl.client;

import net.lebedko.entity.user.User;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.URL;

public class ClientSubmitModifiedOrderCommand extends AbstractCommand {
    private static final String URL_TEMPLATE = URL.CLIENT_ORDER.concat("?").concat(Attribute.ORDER_ID).concat("=");
    private OrderService orderService;

    public ClientSubmitModifiedOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        final User user = context.getSessionAttribute(User.class, Attribute.USER);
        final Long orderId = CommandUtils.parseToLong(context.getRequestParameter(Attribute.ORDER_ID));

        orderService.submitModifiedOrder(orderId, user);
        return new RedirectAction(URL_TEMPLATE.concat(orderId.toString()));
    }
}
