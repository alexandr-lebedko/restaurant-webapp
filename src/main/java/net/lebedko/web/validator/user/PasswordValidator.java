package net.lebedko.web.validator.user;

import net.lebedko.entity.user.Password;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.IValidator;

public class PasswordValidator implements IValidator<Password> {
    private static final Integer MIN_LENGTH = 8;

    @Override
    public void validate(Password password, Errors errors) {
        if (password.getPasswordString().length() < MIN_LENGTH) {
            errors.register("password", "page.error.password");
        }
    }
}
