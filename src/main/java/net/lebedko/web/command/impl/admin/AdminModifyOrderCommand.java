package net.lebedko.web.command.impl.admin;

import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.service.ItemService;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.QueryBuilder;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.URL;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static net.lebedko.web.util.CommandUtils.parseToLong;
import static net.lebedko.web.util.CommandUtils.parseToLongList;


public class AdminModifyOrderCommand extends AbstractAdminCommand {
    private ItemService itemService;

    public AdminModifyOrderCommand(OrderService orderService, ItemService itemService) {
        super(orderService);
        this.itemService = itemService;
    }

    @Override
    protected IResponseAction _doExecute(IContext context) throws ServiceException {
        orderService.modify(parseRequest(context));

        return new RedirectAction(
                QueryBuilder.base(URL.ADMIN_ORDER)
                        .addParam(Attribute.ORDER_ID, getOrderId(context).toString())
                        .toString());
    }

    private Long getOrderId(IContext context) {
        return parseToLong(context.getRequestParameter(Attribute.ORDER_ID));
    }

    private Pair<Order, Collection<OrderItem>> parseRequest(IContext context) {
        final Order order = orderService.getById(CommandUtils.parseToLong(context.getRequestParameter(Attribute.ORDER_ID)));

        final List<Long> orderItemIds = parseToLongList(context.getRequestParameters(Attribute.ORDER_ITEM_ID));
        final List<Long> itemIds = parseToLongList(context.getRequestParameters(Attribute.ITEM_ID));
        final List<Long> itemQuantities = parseToLongList(context.getRequestParameters(Attribute.ORDER_ITEM_QUANTITY));

        final Collection<OrderItem> orderItems = IntStream.range(0, orderItemIds.size())
                .mapToObj(i -> new OrderItem(orderItemIds.get(i), order, itemService.get(itemIds.get(i)), itemQuantities.get(i)))
                .collect(Collectors.toList());

        return Pair.of(order, orderItems);
    }
}
