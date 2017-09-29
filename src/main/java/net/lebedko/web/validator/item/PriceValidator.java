package net.lebedko.web.validator.item;


import net.lebedko.entity.general.Price;
import net.lebedko.web.util.constant.PageErrorNames;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.IValidator;

public class PriceValidator implements IValidator<Price> {
    @Override
    public void validate(Price price, Errors errors) {
        if (price.isValid()) {
            return;
        }

        errors.register("invalid-price", PageErrorNames.INVALID_PRICE);
    }
}
