package net.lebedko.web.validator.item;

import net.lebedko.entity.item.Category;
import net.lebedko.i18n.SupportedLocales;
import net.lebedko.web.util.constant.PageErrorNames;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.IValidator;

/**
 * alexandr.lebedko : 04.08.2017.
 */
public class CategoryValidator implements IValidator<Category> {
    private static final int MIN_TITLE_LENGTH = 2;
    private static final int MAX_TITLE_LENGTH = 50;

    @Override
    public void validate(Category category, Errors errors) {
        if (notValidLength(category)) {
            errors.register("invalid-category", PageErrorNames.INVALID_CATEGORY);
        }
    }

    private boolean notValidLength(Category category) {
        return category.getTitle().getMap().values().stream()
                .map(String::length)
                .anyMatch(length ->
                        (length < MIN_TITLE_LENGTH) || (length > MAX_TITLE_LENGTH));
    }
}
