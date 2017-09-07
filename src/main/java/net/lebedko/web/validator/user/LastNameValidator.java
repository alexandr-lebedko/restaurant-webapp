package net.lebedko.web.validator.user;

import net.lebedko.entity.user.LastName;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.IValidator;

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
