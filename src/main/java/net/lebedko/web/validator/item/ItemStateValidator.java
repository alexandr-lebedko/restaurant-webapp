package net.lebedko.web.validator.item;

import net.lebedko.entity.item.ItemState;
import net.lebedko.web.util.constant.PageErrorNames;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.IValidator;

import static java.util.Objects.nonNull;

/**
 * alexandr.lebedko : 07.09.2017.
 */
public class ItemStateValidator implements IValidator<ItemState> {
    @Override
    public void validate(ItemState state, Errors errors) {
        if(nonNull(state)){
            return;
        }

        errors.register("null-state", PageErrorNames.NULL_ITEM_STATE);
    }
}
