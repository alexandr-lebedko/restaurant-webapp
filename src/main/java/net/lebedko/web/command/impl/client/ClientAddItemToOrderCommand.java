package net.lebedko.web.command.impl.client;

import net.lebedko.entity.item.Item;
import net.lebedko.service.ItemService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.QueryBuilder;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.URL;

import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;

public class ClientAddItemToOrderCommand extends AbstractCommand {
    private ItemService itemService;

    public ClientAddItemToOrderCommand(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        final Map<Item, Long> orderBucket = ofNullable(context.getSessionAttribute(Map.class, Attribute.ORDER_BUCKET))
                .orElse(new HashMap<>());
        final Item item = getItem(context, orderBucket);

        addToOrder(orderBucket, item);
        context.addSessionAttribute(Attribute.ORDER_BUCKET, orderBucket);
        context.addSessionAttribute(Attribute.ORDER_BUCKET_AMOUNT, orderBucket.values()
                .stream()
                .reduce(0L, Long::sum));

        return new RedirectAction(QueryBuilder.base(URL.CLIENT_MENU)
                .addParam(Attribute.CATEGORY_ID, Long.toString(item.getCategory().getId()))
                .toString()
        );
    }

    private void addToOrder(Map<Item, Long> content, Item item) {
        content.computeIfPresent(item, (k, v) -> v + 1);
        content.putIfAbsent(item, 1L);
    }

    private Item getItem(IContext context, Map<Item, Long> orderBucket) throws ServiceException {
        final Long itemId = CommandUtils.parseToLong(context.getRequestParameter(Attribute.ITEM_ID), -1L);
        return orderBucket.keySet().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElse(itemService.get(itemId));
    }
}
