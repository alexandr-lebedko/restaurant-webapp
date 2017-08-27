package net.lebedko.web.validator;

import net.lebedko.entity.demo.item.Category;
import net.lebedko.i18n.SupportedLocales;
import net.lebedko.web.util.constant.PageErrorNames;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static net.lebedko.i18n.SupportedLocales.getLocales;

/**
 * alexandr.lebedko : 04.08.2017.
 */
public class CategoryValidator implements IValidator<Category> {


    @Override
    public void validate(Category category, Errors errors) {
        if (isContainsAllSupportedLocales(category) && category.isValid()) {
            return;
        }
        errors.register("invalid-category", PageErrorNames.INVALID_CATEGORY);
    }

    private boolean isContainsAllSupportedLocales(Category category) {
        return category.getValue().getMap().keySet().containsAll(getLocales().values());
    }
}
