package net.lebedko.web.validator.user;

import net.lebedko.entity.user.Password;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.IValidator;

/**
 * alexandr.lebedko : 19.06.2017
 */
public class PasswordValidator implements IValidator<Password> {
    @Override
    public void validate(Password password, Errors errors) {
        if (password.isValid())
            return;

        errors.register("password", "page.error.password");
    }
}
