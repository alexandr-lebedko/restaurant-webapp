package net.lebedko.web.validator.user;

import net.lebedko.entity.user.FirstName;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.IValidator;

import java.util.Map;

/**
 * alexandr.lebedko : 15.06.2017
 */
public class FirstNameValidator implements IValidator<FirstName> {
    @Override
    public void validate(FirstName firstName, Errors errors) {
        if (firstName.isValid())
            return;

        errors.register("firstName", "page.error.firstName");
    }
}
