package net.lebedko.web.command.impl.client;

import net.lebedko.entity.item.Item;
import net.lebedko.service.ItemService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
        final Map<Item, Integer> orderContent = getOrderContent(context);
        final Item item = getItem(context);

        addToOrder(orderContent, item);
        addOrderContentToSession(context, orderContent);

        return redirectToItemsByCategory(context, item);
    }

    private void addOrderContentToSession(IContext context, Map<Item, Integer> content) {
        context.addSessionAttribute("orderContent", content);
    }

    private void addToOrder(Map<Item, Integer> content, Item item) {
        content.putIfAbsent(item, 1);
        content.computeIfPresent(item, (k, v) -> v + 1);
    }


    private Long getItemId(IContext context) {
        Long id = -1L;
        try {
            id = Long.valueOf(context.getRequestParameter("itemId"));
        } catch (NumberFormatException nfe) {
            LOG.error(nfe);
        }
        return id;
    }

    @SuppressWarnings("unchecked")
    private Map<Item, Integer> getOrderContent(IContext context) {
        return ofNullable(context.getSessionAttribute(Map.class, "orderContent")).orElse(new HashMap<>());
    }

    private Item getItem(IContext context) throws ServiceException {
        final Long itemId = getItemId(context);
        final Map<Item, Integer> content = getOrderContent(context);

        Optional<Item> itemOptional = content.keySet().stream()
                .filter(item -> item.getId() == itemId)
                .findFirst();

        if (itemOptional.isPresent()) {
            return itemOptional.get();
        } else {
            return itemService.get(itemId);
        }
    }

    private IResponseAction redirectToItemsByCategory(IContext context, Item item) {
        return new RedirectAction(URL.CLIENT_MENU_ITEMS + "?category=" + item.getCategory().getId());
    }

}
