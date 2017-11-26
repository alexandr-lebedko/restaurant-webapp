package net.lebedko.web.validator.item;

import net.lebedko.entity.item.Description;
import net.lebedko.entity.item.Title;
import net.lebedko.i18n.SupportedLocales;
import net.lebedko.web.util.constant.PageErrorNames;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.IValidator;

import static java.util.Objects.isNull;


public class DescriptionValidator implements IValidator<Description> {
    private static final Integer MIN_SIZE = 20;
    private static final Integer MAX_SIZE = 255;

    @Override
    public void validate(Description description, Errors errors) {
        if (notValid(description)) {
            errors.register("invalidDescription", PageErrorNames.INVALID_DESCRIPTION);
        }
    }

    private boolean notValid(Description description) {
        return description.getValue().getMap().values().stream()
                .map(String::length)
                .anyMatch(length ->
                        (length < MIN_SIZE) || (length > MAX_SIZE));
    }
}
