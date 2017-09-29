package net.lebedko.web.validator.item;

import net.lebedko.entity.item.Title;
import net.lebedko.i18n.SupportedLocales;
import net.lebedko.web.util.constant.PageErrorNames;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.IValidator;

import static java.util.Objects.isNull;


public class TitleValidator implements IValidator<Title> {
    @Override
    public void validate(Title title, Errors errors) {
        if (isNull(title)) {
            errors.register("nullTitle", "Title cannot be null");
            return;
        }

        if (containsSupportedLocales(title) && title.isValid()) {
            return;
        }

        errors.register("invalidTitle", PageErrorNames.INVALID_TITLE);

    }

    private boolean containsSupportedLocales(Title title) {
        return SupportedLocales.containsSupportedLocales(title.getValue().getMap().keySet());
    }
}
