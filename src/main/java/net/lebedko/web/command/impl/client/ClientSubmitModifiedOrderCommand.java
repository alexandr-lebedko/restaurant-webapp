package net.lebedko.web.command.impl.client;

import net.lebedko.entity.user.User;
import net.lebedko.service.OrderService;
import net.lebedko.web.command.ICommand;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.QueryBuilder;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.URL;

public class ClientSubmitModifiedOrderCommand implements ICommand{
    private OrderService orderService;

    public ClientSubmitModifiedOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public IResponseAction execute(IContext context) {
        final User user = context.getSessionAttribute(User.class, Attribute.USER);
        final Long orderId = CommandUtils.parseToLong(context.getRequestParameter(Attribute.ORDER_ID));

        orderService.submitModifiedOrder(orderId, user);
        return new RedirectAction(
                QueryBuilder.base(URL.CLIENT_ORDER)
                        .addParam(Attribute.ORDER_ID, orderId.toString())
                        .build());
    }
}
