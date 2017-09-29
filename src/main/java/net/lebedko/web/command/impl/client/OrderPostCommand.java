package net.lebedko.web.command.impl.client;

import net.lebedko.entity.demo.order.Order;
import net.lebedko.entity.demo.order.OrderContent;
import net.lebedko.entity.demo.order.OrderInfo;
import net.lebedko.service.demo.MenuItemService;
import net.lebedko.service.demo.OrderService;
import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.constant.PageErrorNames;
import net.lebedko.web.util.constant.WebConstant;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.order.OrderValidator;


import static java.util.Optional.ofNullable;
import static net.lebedko.entity.demo.order.OrderContent.*;
import static net.lebedko.web.util.constant.WebConstant.*;

/**
 * alexandr.lebedko : 20.09.2017.
 */
public class OrderPostCommand extends AbstractCommand {
    private static final IResponseAction ORDER_FORM_FORWARD = new ForwardAction(PAGE.CLIENT_ORDER_FORM);

    private OrderService orderService;
    private OrderValidator orderValidator;

    public OrderPostCommand(OrderService orderService, OrderValidator orderValidator) {
        this.orderService = orderService;
        this.orderValidator = orderValidator;
    }

    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        final Errors errors = new Errors();

        final OrderContent orderContent = parseOrderContent(context, errors);
        final Order order = new Order(new OrderInfo(), orderContent);

        validateOrder(order, errors);

        if (errors.hasErrors()) {
            return ORDER_FORM_FORWARD;
        }

        orderService.createOrder(order);
        return null;
    }

    private void validateOrder(Order order, Errors errors) {
        orderValidator.validate(order, errors);
    }

    private OrderContent getOrderContent(IContext context) {
        return ofNullable(context.getSessionAttribute(OrderContent.class, "orderContent")).orElse(new OrderContent());
    }

    private OrderContent parseOrderContent(IContext context, Errors errors) {
        final OrderContent content = getOrderContent(context);


        final String[] idValues = context.getRequestParameters("itemId");
        final String[] quantityValues = context.getRequestParameters("quantity");

        for (int i = 0; i < idValues.length; i++) {
            long itemId = parseItemId(idValues[i]);
            int quantity = parseItemQuantity(quantityValues[i]);

            try {
                content.set(getById(content, itemId), quantity);
            } catch (IllegalArgumentException e) {
                LOG.error(e);
                errors.register("invalidOrderPosition", PageErrorNames.INVALID_ORDER_POSITION);
            }
        }

        return content;
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
