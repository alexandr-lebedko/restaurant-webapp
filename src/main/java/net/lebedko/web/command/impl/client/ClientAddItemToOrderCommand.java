package net.lebedko.web.command.impl.client;

import net.lebedko.entity.item.Item;
import net.lebedko.service.ItemService;
import net.lebedko.service.OrderBucket;
import net.lebedko.web.command.Command;
import net.lebedko.web.command.Context;
import net.lebedko.web.response.ResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.CommandUtils;
import net.lebedko.web.util.QueryBuilder;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.URL;

import static java.util.Optional.ofNullable;

public class ClientAddItemToOrderCommand implements Command {
    private ItemService itemService;

    public ClientAddItemToOrderCommand(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public ResponseAction execute(Context context) {
        final OrderBucket bucket = getOrderBucket(context);
        final Item item = getItem(context, bucket);

        bucket.add(item);
        context.addSessionAttribute(Attribute.ORDER_BUCKET, bucket);

        return new RedirectAction(
                QueryBuilder.base(URL.CLIENT_MENU)
                        .addParam(Attribute.CATEGORY_ID, Long.toString(item.getCategory().getId()))
                        .build()
        );
    }

    private Item getItem(Context context, OrderBucket bucket) {
        final Long id = CommandUtils.parseToLong(context.getRequestParameter(Attribute.ITEM_ID));
        return ofNullable(bucket.getItem(id))
                .orElse(itemService.get(id));
    }

    private OrderBucket getOrderBucket(Context context) {
        return ofNullable(context.getSessionAttribute(OrderBucket.class, Attribute.ORDER_BUCKET))
                .orElse(new OrderBucket());
    }
}