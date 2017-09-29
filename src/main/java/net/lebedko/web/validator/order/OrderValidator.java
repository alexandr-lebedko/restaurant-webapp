package net.lebedko.web.validator.order;

import net.lebedko.entity.order.Order;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.IValidator;
import net.lebedko.web.validator.item.MenuItemValidator;


/**
 * alexandr.lebedko : 24.09.2017.
 */
public class OrderValidator implements IValidator<Order> {
    private MenuItemValidator itemValidator;

    public OrderValidator(MenuItemValidator itemValidator) {
        this.itemValidator = itemValidator;
    }

    public OrderValidator() {
        this.itemValidator = new MenuItemValidator();
    }

    @Override
    public void validate(Order order, Errors errors) {

    }
}