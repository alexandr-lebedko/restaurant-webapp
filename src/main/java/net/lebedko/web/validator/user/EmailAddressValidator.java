package net.lebedko.web.validator.user;

import net.lebedko.entity.general.EmailAddress;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.IValidator;


/**
 * alexandr.lebedko : 19.06.2017
 */
public class EmailAddressValidator implements IValidator<EmailAddress> {
    @Override
    public void validate(EmailAddress emailAddress, Errors errors) {
        if (emailAddress.isValid())
            return;

        errors.register("email", "page.error.email");
    }
}
