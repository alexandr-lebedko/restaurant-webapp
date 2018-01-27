package net.lebedko.web.command.impl.admin;

import java.util.List;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import net.lebedko.entity.item.Item;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.service.ItemService;
import net.lebedko.service.OrderService;
import net.lebedko.web.command.Context;
import net.lebedko.web.response.ResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.QueryBuilder;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.URL;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static net.lebedko.web.util.CommandUtils.parseToLong;
import static net.lebedko.web.util.CommandUtils.parseToLongList;

public class AdminModifyOrderCommand extends AbstractAdminCommand {
    private ItemService itemService;

    public AdminModifyOrderCommand(OrderService orderService, ItemService itemService) {
        super(orderService);
        this.itemService = itemService;
    }

    @Override
    protected ResponseAction doExecute(Context context) {
        final Collection<OrderItem> modifiedOrderItems = parseOrderItems(context);
        orderService.modify(modifiedOrderItems);
        return new RedirectAction(QueryBuilder.base(URL.ADMIN_ORDER)
                .addParam(Attribute.ORDER_ID, getOrderId(context).toString())
                .build());
    }

    private Long getOrderId(Context context) {
        return parseToLong(context.getRequestParameter(Attribute.ORDER_ID));
    }

    private Collection<OrderItem> parseOrderItems(Context context) {
        final Order order = ofNullable(getOrderId(context))
                .map(orderService::getById)
                .orElseThrow(NoSuchElementException::new);
        final List<Item> items = parseToLongList(context.getRequestParameters(Attribute.ITEM_ID)).stream()
                .map(itemService::get)
                .collect(toList());
        final List<Long> quantities = parseToLongList(context.getRequestParameters(Attribute.ORDER_ITEM_QUANTITY));

        return IntStream.range(0, items.size())
                .mapToObj(i -> new OrderItem(order, items.get(i), quantities.get(i)))
                .collect(toList());
    }
}