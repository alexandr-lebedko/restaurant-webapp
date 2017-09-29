package net.lebedko.web.command.impl.client;

import net.lebedko.entity.item.Item;
import net.lebedko.entity.order.OrderContent;
import net.lebedko.service.ItemService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static net.lebedko.web.util.constant.WebConstant.*;

/**
 * alexandr.lebedko : 19.09.2017.
 */
public class AddItemToOrderContentCommand extends AbstractCommand {
    private ItemService itemService;

    public AddItemToOrderContentCommand(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        final OrderContent orderContent = getOrderContent(context);
        final Item item = getItem(context);

        if (nonNull(item)) {
            orderContent.add(item);
            context.addSessionAttribute("orderContent", orderContent);
        }

        return redirectToItemsByCategory(context, item);
    }

    private IResponseAction redirectToItemsByCategory(IContext context, Item item) {
        return new RedirectAction(URL.CLIENT_MENU_ITEMS + "?category=" + item.getCategory().getId());
    }

    private Item getItem(IContext context) throws ServiceException {
        final OrderContent orderContent = getOrderContent(context);
        Long itemId = getItemId(context);

        Item item = OrderContent.getById(orderContent, itemId);
        if (nonNull(item)) {
            return item;
        } else
            return itemService.get(itemId);
    }

    private Long getItemId(IContext context) {
        Long id = -1L;
        try {
            id = Long.valueOf(context.getRequestParameter("itemId"));
        } catch (NumberFormatException nfe) {
            //TODO:correctly register/log errors
        }
        return id;
    }

    private OrderContent getOrderContent(IContext context) {
        return ofNullable(context.getSessionAttribute(OrderContent.class, "orderContent")).orElse(new OrderContent());
    }
}
