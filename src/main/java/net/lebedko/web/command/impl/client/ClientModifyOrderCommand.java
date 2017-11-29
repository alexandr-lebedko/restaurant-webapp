package net.lebedko.web.command.impl.client;

import net.lebedko.entity.item.Item;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderItem;
import net.lebedko.entity.user.User;
import net.lebedko.service.OrderItemService;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.URL;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

public class ClientModifyOrderCommand extends AbstractCommand {
    private static final IResponseAction CLIENT_ORDER_REDIRECT = new RedirectAction(URL.CLIENT_ORDER_FORM);

    private OrderService orderService;
    private OrderItemService orderItemService;

    public ClientModifyOrderCommand(OrderService orderService, OrderItemService orderItemService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        final Long orderId = CommandUtils.parseToLong(context.getRequestParameter(Attribute.ORDER_ID), -1L);
        final User user = context.getSessionAttribute(User.class, Attribute.USER);

        final Order order = orderService.getById(orderId);
        final Collection<OrderItem> orderItems = orderItemService.getOrderItems(order);

        orderService.delete(orderId, user);

        final Map<Item, Long> mergedBucket = mergeBucketAndOrderItems(getOrderBucket(context), orderItems);

        context.addSessionAttribute(Attribute.ORDER_BUCKET, mergedBucket);
        context.addSessionAttribute(Attribute.ORDER_BUCKET_AMOUNT, mergedBucket.values()
                .stream()
                .reduce(0L, Long::sum));

        return CLIENT_ORDER_REDIRECT;
    }


    @SuppressWarnings("unchecked")
    private Map<Item, Long> getOrderBucket(IContext context) {
        return ofNullable(context.getSessionAttribute(Map.class, Attribute.ORDER_BUCKET)).orElse(new HashMap<>());
    }

    private Map<Item, Long> mergeBucketAndOrderItems(Map<Item, Long> bucketContent, Collection<OrderItem> orderItems) {
        Map<Item, Long> orderContent = orderItems.stream()
                .collect(Collectors.toMap(
                        OrderItem::getItem,
                        OrderItem::getQuantity));

        return Stream.of(bucketContent, orderContent)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Long::sum));
    }
}
