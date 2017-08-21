package net.lebedko.web.validator;

import net.lebedko.entity.demo.item.Category;
import net.lebedko.web.util.constant.PageErrorNames;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * alexandr.lebedko : 04.08.2017.
 */
public class CategoryValidator implements IValidator<Category> {
    private final static Set<Locale> SUPPORTED_LOCALES = new HashSet<>();

    static {
        SUPPORTED_LOCALES.add(Locale.ENGLISH);
        SUPPORTED_LOCALES.add(new Locale("ukr"));
        SUPPORTED_LOCALES.add(new Locale("ru"));
    }


    @Override
    public void validate(Category category, Errors errors) {
        if(category.getValue().getMap().keySet().containsAll(SUPPORTED_LOCALES) && category.isValid()){
            return;
        }
        errors.register("invalid-category", PageErrorNames.INVALID_CATEGORY);

    }
}
