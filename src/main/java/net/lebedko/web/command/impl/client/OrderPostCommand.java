package net.lebedko.web.command.impl.client;

import net.lebedko.entity.item.Item;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.user.User;
import net.lebedko.service.OrderService;
import net.lebedko.service.exception.ClosedInvoiceException;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.response.RedirectAction;
import net.lebedko.web.util.constant.PageErrorNames;
import net.lebedko.web.util.constant.URL;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.order.OrderValidator;


import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static net.lebedko.web.util.constant.PageErrorNames.ORDER_FORM_ERROR;
import static net.lebedko.web.util.constant.PageErrorNames.UNPAID_INVOICE;
import static net.lebedko.web.util.constant.WebConstant.*;

/**
 * alexandr.lebedko : 20.09.2017.
 */

public class OrderPostCommand extends AbstractCommand {
    private static final IResponseAction ORDER_FORM_FORWARD = new ForwardAction(PAGE.CLIENT_ORDER_FORM);
    private static final IResponseAction ORDER_FORM_REDIRECT = new RedirectAction(URL.CLIENT_ORDER_FORM);
    private static final IResponseAction ORDERS_REDIRECT = new RedirectAction(URL.CLIENT_ORDERS);

    private OrderService orderService;
    private OrderValidator validator;

    public OrderPostCommand(OrderService orderService, OrderValidator validator) {
        this.orderService = orderService;
        this.validator = validator;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        final Errors errors = new Errors();

        Map<Long, Integer> amountToIds = parseOrderForm(context, errors);

        if (errors.hasErrors()) {
            context.addErrors(errors);
            return ORDER_FORM_FORWARD;
        }

        if (amountToIds.isEmpty()) {
            clearOrderBucketInfo(context);
            return ORDER_FORM_REDIRECT;
        }

        Map<Item, Integer> orderContent = orderService.toOrderContent(amountToIds);

        if (validator.notValid(orderContent, errors)) {
            context.addErrors(errors);
            return ORDER_FORM_FORWARD;
        }

        try {
            orderService.createOrder(getUserFromSession(context), orderContent);
            clearOrderBucketInfo(context);

            return ORDERS_REDIRECT;
        } catch (ClosedInvoiceException cie) {
            LOG.error(cie);
            errors.register("unpaidInvoice", UNPAID_INVOICE);
        }

        context.addErrors(errors);

        return ORDER_FORM_FORWARD;
    }

    private void clearOrderBucketInfo(IContext context) {
        context.removeSessionAttribute("orderContent");
        context.removeSessionAttribute("orderAmount");
    }

    private User getUserFromSession(IContext context) {
        return context.getSessionAttribute(User.class, "user");
    }

    private Map<Long, Integer> parseOrderForm(IContext context, Errors errors) {
        final List<Long> ids = getIds(context);
        final List<Integer> amounts = getAmounts(context);

        if (ids.size() != amounts.size()) {
            errors.register("formError", ORDER_FORM_ERROR);
            return null;
        }

        return IntStream.range(0, ids.size())
                .boxed()
                .collect(toMap(ids::get, amounts::get));
    }


    private List<Integer> getAmounts(IContext context) {
        return context.getRequestParameters("item-amount").stream()
                .map(this::parseItemQuantity)
                .collect(toList());
    }

    private List<Long> getIds(IContext context) {
        return context.getRequestParameters("item-id").stream()
                .map(this::parseItemId)
                .collect(toList());
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
