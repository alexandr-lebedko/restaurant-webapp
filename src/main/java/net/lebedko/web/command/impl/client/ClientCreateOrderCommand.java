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
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class ClientCreateOrderCommand extends AbstractCommand {
    private static final IResponseAction ORDERS_REDIRECT = new RedirectAction(URL.CLIENT_ORDERS);
    private OrderService orderService;

    public ClientCreateOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        final Collection<Pair<Long, Long>> quantityToItem = parseOrderForm(context);
        final User user = context.getSessionAttribute(User.class, Attribute.USER);

        orderService.createOrder(user, quantityToItem);

        context.removeSessionAttribute(Attribute.ORDER_BUCKET);
        context.removeSessionAttribute(Attribute.ORDER_BUCKET_AMOUNT);

        return ORDERS_REDIRECT;
    }

    private Collection<Pair<Long, Long>> parseOrderForm(IContext context) {
        final List<Long> ids = context.getRequestParameters(Attribute.ITEM_ID).stream()
                .map(value -> CommandUtils.parseToLong(value, -1L))
                .filter(value -> value > 0)
                .collect(toList());
        final List<Long> amounts = context.getRequestParameters(Attribute.ORDER_ITEM_QUANTITY).stream()
                .map(value -> CommandUtils.parseToLong(value, -1L))
                .filter(value -> value > 0)
                .collect(toList());

        return IntStream.range(0, ids.size())
                .boxed()
                .map(i -> Pair.of(ids.get(i), amounts.get(i)))
                .collect(toList());
    }
}
