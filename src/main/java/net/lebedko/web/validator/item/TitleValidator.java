package net.lebedko.web.validator.item;

import net.lebedko.entity.item.Title;
import net.lebedko.web.util.constant.PageErrorNames;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.IValidator;


public class TitleValidator implements IValidator<Title> {
    private static final Integer MIN_SIZE = 2;
    private static final Integer MAX_SIZE = 50;

    @Override
    public void validate(Title title, Errors errors) {
        if (notValid(title)) {
            errors.register("invalidTitle", PageErrorNames.INVALID_TITLE);
        }
    }

    private boolean notValid(Title title) {
        return title.getValue().getMap().values().stream()
                .map(String::length)
                .anyMatch(length ->
                        (length < MIN_SIZE) || (length > MAX_SIZE));
    }

}
