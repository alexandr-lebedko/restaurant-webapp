package net.lebedko.web.command.impl.admin;

import net.lebedko.service.OrderService;
import net.lebedko.web.command.Context;
import net.lebedko.web.response.ResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.QueryBuilder;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.URL;

public class AdminProcessOrderCommand extends AbstractAdminCommand {
    public AdminProcessOrderCommand(OrderService orderService) {
        super(orderService);
    }

    @Override
    protected ResponseAction doExecute(Context context) {
        final Long orderId = CommandUtils.parseToLong(context.getRequestParameter(Attribute.ORDER_ID));
        orderService.process(orderId);
        return new RedirectAction(QueryBuilder.base(URL.ADMIN_ORDER)
                        .addParam(Attribute.ORDER_ID, orderId.toString())
                        .build());
    }
}
