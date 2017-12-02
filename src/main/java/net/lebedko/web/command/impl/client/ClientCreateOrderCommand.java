package net.lebedko.web.command.impl.client;

import net.lebedko.entity.item.Item;
import net.lebedko.entity.user.User;
import net.lebedko.service.OrderBucket;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.URL;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ClientCreateOrderCommand extends AbstractCommand {
    private static final IResponseAction ORDERS_REDIRECT = new RedirectAction(URL.CLIENT_ORDERS);
    private OrderService orderService;

    public ClientCreateOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        final User user = context.getSessionAttribute(User.class, Attribute.USER);
        final OrderBucket bucket = parseBucketForm(context);

        orderService.createOrder(user, bucket);
        context.removeSessionAttribute(Attribute.ORDER_BUCKET);

        return ORDERS_REDIRECT;
    }


    private OrderBucket parseBucketForm(IContext context) {
        final OrderBucket sessionBucket = CommandUtils.getOrderBucketFromSession(context);

        final List<Item> items = context.getRequestParameters(Attribute.ITEM_ID).stream()
                .map(CommandUtils::parseToLong)
                .map(sessionBucket::getItem)
                .collect(toList());

        final List<Long> quantities = context.getRequestParameters(Attribute.ITEM_ID).stream()
                .map(CommandUtils::parseToLong)
                .collect(toList());

        final OrderBucket formBucket = new OrderBucket();
        for (int i = 0; i < items.size(); i++) {
            formBucket.add(items.get(i), quantities.get(i));
        }
        return formBucket;
    }
}
