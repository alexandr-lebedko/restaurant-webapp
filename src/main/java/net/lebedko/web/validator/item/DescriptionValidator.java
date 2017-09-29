package net.lebedko.web.validator.item;

import net.lebedko.entity.item.Description;
import net.lebedko.i18n.SupportedLocales;
import net.lebedko.web.util.constant.PageErrorNames;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.IValidator;

import static java.util.Objects.isNull;


public class DescriptionValidator implements IValidator<Description> {
    @Override
    public void validate(Description description, Errors errors) {
        if (isNull(description)) {
            errors.register("null-description", PageErrorNames.NULL_DESCRIPTION);
            return;
        }

        if (containsSupportedLocales(description) && description.isValid()) {
            return;
        }

        errors.register("invalidDescription", PageErrorNames.INVALID_DESCRIPTION);

    }

    private boolean containsSupportedLocales(Description description) {
        return SupportedLocales.containsSupportedLocales(description.getValue().getMap().keySet());
    }
}
