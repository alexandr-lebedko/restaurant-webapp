package net.lebedko.web.validator.order;

import net.lebedko.entity.item.Item;
import net.lebedko.entity.order.Order;
import net.lebedko.web.util.constant.PageErrorNames;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.IValidator;

import java.util.Map;


/**
 * alexandr.lebedko : 24.09.2017.
 */
public class OrderValidator implements IValidator<Map<Item, Integer>> {
    public OrderValidator() {
    }

    @Override
    public void validate(Map<Item, Integer> quantityToItem, Errors errors) {
        if (quantityToItem.isEmpty()) {
            errors.register("emptyOrder", PageErrorNames.EMPTY_ORDER);
            return;
        }

        if (quantityToItem.entrySet().stream()
                .anyMatch(e -> e.getValue() <= 0)) {
            errors.register("negativeQuantity", PageErrorNames.NEGATIVE_ITEM_QUANTITY);
        }
    }

}