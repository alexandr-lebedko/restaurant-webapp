package net.lebedko.web.validator.item;

import net.lebedko.entity.item.Category;
import net.lebedko.i18n.SupportedLocales;
import net.lebedko.web.util.constant.PageErrorNames;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.IValidator;

import static java.util.Objects.isNull;

/**
 * alexandr.lebedko : 04.08.2017.
 */
public class CategoryValidator implements IValidator<Category> {


    @Override
    public void validate(Category category, Errors errors) {
        if (isNull(category)) {
            errors.register("nullCategory", PageErrorNames.NULL_CATEGORY);
            return;
        }

        if (containsSupportedLocales(category) && category.isValid()) {
            return;
        }
        errors.register("invalid-category", PageErrorNames.INVALID_CATEGORY);
    }

    private boolean containsSupportedLocales(Category category) {
        return SupportedLocales.containsSupportedLocales(category.getValue().getMap().keySet());
    }
}
