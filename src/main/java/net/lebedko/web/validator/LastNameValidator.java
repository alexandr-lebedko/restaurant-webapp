package net.lebedko.web.validator;

import net.lebedko.entity.user.LastName;

import java.util.Map;

/**
 * alexandr.lebedko : 15.06.2017
 */
public class LastNameValidator implements IValidator<LastName> {
    @Override
    public void validate(LastName familyName, Errors errors) {
        if (familyName.isValid())
            return;

        errors.register("lastName", "page.error.lastName");
    }
}
