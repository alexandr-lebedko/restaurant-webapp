package net.lebedko.web.validator;

import net.lebedko.entity.general.Password;

import java.util.Map;

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
