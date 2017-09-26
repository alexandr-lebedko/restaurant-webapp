package net.lebedko.web.command.impl.client;

import net.lebedko.entity.demo.item.MenuItem;
import net.lebedko.entity.demo.order.OrderContent;
import net.lebedko.service.demo.MenuItemService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;

import java.util.Optional;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static net.lebedko.web.util.constant.WebConstant.*;

/**
 * alexandr.lebedko : 19.09.2017.
 */
public class AddItemToOrderContentCommand extends AbstractCommand {
    private MenuItemService itemService;

    public AddItemToOrderContentCommand(MenuItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        final OrderContent orderContent = getOrderContent(context);
        final MenuItem item = getItem(context);

        if (nonNull(item)) {
            orderContent.add(item);
            context.addSessionAttribute("orderContent", orderContent);
        }

        return redirectToItemsByCategory(context, item);
    }

    private IResponseAction redirectToItemsByCategory(IContext context, MenuItem item) {
        return new RedirectAction(URL.CLIENT_MENU_ITEMS + "?category=" + item.getCategory().getId());
    }

    private MenuItem getItem(IContext context) throws ServiceException {
        final OrderContent orderContent = getOrderContent(context);
        Long itemId = getItemId(context);

        Optional<MenuItem> itemOptional = OrderContent.getById(orderContent, itemId);
        if (itemOptional.isPresent()) {
            return itemOptional.get();
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
