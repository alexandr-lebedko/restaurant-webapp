package net.lebedko.web.validator.item;

import net.lebedko.entity.item.Category;
import net.lebedko.web.util.constant.PageErrorNames;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.Validator;

public class CategoryValidator implements Validator<Category> {
    private static final int MIN_TITLE_LENGTH = 2;
    private static final int MAX_TITLE_LENGTH = 25;

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