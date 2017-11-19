package net.lebedko.web.command.impl.client;

import net.lebedko.entity.item.Item;
import net.lebedko.service.ItemService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.constant.Attribute;
import net.lebedko.web.util.constant.URL;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * alexandr.lebedko : 19.09.2017.
 */
public class ClientAddItemToOrderCommand extends AbstractCommand {
    private ItemService itemService;

    public ClientAddItemToOrderCommand(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        final Map<Item, Integer> orderContent = getOrderContent(context);
        final Item item = getItem(context);

        addToOrder(orderContent, item);
        addOrderContentToSession(context, orderContent);
        addItemsAmountToSession(context, orderContent);

        return new RedirectAction(URL.CLIENT_MENU.concat("?").concat(Attribute.CATEGORY_ID).concat("=").concat(Long.toString(item.getCategory().getId())));
    }

    private void addOrderContentToSession(IContext context, Map<Item, Integer> content) {
        context.addSessionAttribute("orderContent", content);
    }

    private void addToOrder(Map<Item, Integer> content, Item item) {
        content.computeIfPresent(item, (k, v) -> v + 1);
        content.putIfAbsent(item, 1);
    }

    private void addItemsAmountToSession(IContext context, Map<Item, Integer> content) {
        Long amount = content.values().stream()
                .mapToLong(Long::valueOf)
                .sum();

        context.addSessionAttribute(Attribute.ORDER_BUCKET_AMOUNT, amount);
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
        return new RedirectAction(URL.CLIENT_ITEM_ADD.concat("?").concat(Attribute.CATEGORY_ID).concat(Long.toString(item.getId())));

    }

}