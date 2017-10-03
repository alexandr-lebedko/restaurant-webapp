package net.lebedko.web.command.impl.client;

import net.lebedko.entity.item.Item;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.user.User;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.order.OrderValidator;


import java.util.*;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static net.lebedko.web.util.constant.WebConstant.*;

/**
 * alexandr.lebedko : 20.09.2017.
 */
public class OrderPostCommand extends AbstractCommand {
    private static final IResponseAction ORDER_FORM_FORWARD = new ForwardAction(PAGE.CLIENT_ORDER_FORM);
    private static final IResponseAction SUCCESS_PAGE_REDIRECT = new RedirectAction(URL.SUCCESS_ORDER);

    private OrderService orderService;
    private OrderValidator validator;

    public OrderPostCommand(OrderService orderService, OrderValidator validator) {
        this.orderService = orderService;
        this.validator = validator;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        final Errors errors = new Errors();

        final User user = getUserFromSession(context);
        final Map<Item, Integer> orderContent = getOrderContent(context);

        validator.validate(orderContent, errors);

        if (errors.hasErrors()) {
            LOG.warn("Attempt to create invalid Order. Order Content: isEmpty:" + orderContent.isEmpty() + ", content: " + orderContent);
            context.addErrors(errors);
            return ORDER_FORM_FORWARD;
        }

        final Order order = orderService.createOrder(user, orderContent);
        context.addRequestAttribute("order", order);

        return SUCCESS_PAGE_REDIRECT;
    }

    private User getUserFromSession(IContext context) {
        return context.getSessionAttribute(User.class, "user");
    }

    private Map<Item, Integer> getOrderContent(IContext context) {
        final Map<Item, Integer> orderContent = getOrderContentFromSession(context);
        final List<Long> itemIds = getItemIdsFromForm(context);
        final List<Integer> quantities = getItemQuantitiesForm(context);

        if (itemIds.size() != quantities.size()) {
            //TODO :: this case may happen only if form is forged
            return Collections.emptyMap();
        }
        for (int i = 0; i < itemIds.size(); i++) {
            Long id = itemIds.get(i);
            Integer quantity = quantities.get(i);

            orderContent.keySet().stream()
                    .filter(item -> id.equals(item.getId()))
                    .findFirst()
                    .ifPresent(item -> orderContent.put(item, quantity));
        }
        return orderContent;
    }

    private List<Integer> getItemQuantitiesForm(IContext context) {
        return context.getRequestParameters("quantity").stream()
                .map(this::parseItemQuantity)
                .collect(toList());
    }

    private List<Long> getItemIdsFromForm(IContext context) {
        return context.getRequestParameters("itemId").stream()
                .map(this::parseItemId)
                .collect(toList());
    }

    @SuppressWarnings("unchecked")
    private Map<Item, Integer> getOrderContentFromSession(IContext context) {
        return ofNullable(context.getSessionAttribute(Map.class, "orderContent")).orElse(new HashMap());
    }

    private long parseItemId(String value) {
        long id = -1;
        try {
            id = Long.valueOf(value);
        } catch (NumberFormatException nfe) {
            LOG.error(nfe);
        }
        return id;
    }

    private int parseItemQuantity(String value) {
        int quantity = -1;
        try {
            quantity = Integer.valueOf(value);
        } catch (NumberFormatException nfe) {
            LOG.error(nfe);
        }
        return quantity;
    }
}
