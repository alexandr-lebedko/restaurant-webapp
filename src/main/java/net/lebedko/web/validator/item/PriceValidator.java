package net.lebedko.web.validator.item;


import net.lebedko.entity.general.Price;
import net.lebedko.web.util.constant.PageErrorNames;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.Validator;

public class PriceValidator implements Validator<Price> {
    private static final Double MIN_VALUE = 0d;

    @Override
    public void validate(Price price, Errors errors) {
        if (notValid(price)) {
            errors.register("invalid-price", PageErrorNames.INVALID_PRICE);
        }
    }

    private boolean notValid(Price price) {
        return price.getValue() < MIN_VALUE;
    }
}
