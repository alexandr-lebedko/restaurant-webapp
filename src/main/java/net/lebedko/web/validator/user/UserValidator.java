package net.lebedko.web.validator.user;

import net.lebedko.entity.user.User;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.IValidator;

import static java.util.Objects.*;

/**
 * alexandr.lebedko : 19.06.2017
 */
public class UserValidator implements IValidator<User> {
    private FullNameValidator fullNameValidator;
    private EmailAddressValidator emailAddressValidator;
    private PasswordValidator passwordValidator;

    public UserValidator() {
        this(new FullNameValidator(), new EmailAddressValidator(), new PasswordValidator());
    }

    public UserValidator(FullNameValidator fullNameValidator, EmailAddressValidator emailAddressValidator, PasswordValidator passwordValidator) {
        this.fullNameValidator = requireNonNull(fullNameValidator, "FullNameValidator is required and cannot be null!");
        this.emailAddressValidator = requireNonNull(emailAddressValidator, "EmailAddressValidator is required and cannot be null!");
        this.passwordValidator = requireNonNull(passwordValidator, "PasswordValidator is required and cannot be null!");
    }

    @Override
    public void validate(User user, Errors errors) {
        if (user.isValid())
            return;

        fullNameValidator.validate(user.getFullName(), errors);
        emailAddressValidator.validate(user.getEmail(), errors);
        passwordValidator.validate(user.getPassword(), errors);
    }
}
