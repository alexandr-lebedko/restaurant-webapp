package net.lebedko.web.validator.user;

import net.lebedko.entity.user.User;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.IValidator;


public class UserValidator implements IValidator<User> {
    private FullNameValidator fullNameValidator;
    private EmailAddressValidator emailAddressValidator;
    private PasswordValidator passwordValidator;

    public UserValidator() {
        this(new FullNameValidator(), new EmailAddressValidator(), new PasswordValidator());
    }

    public UserValidator(FullNameValidator fullNameValidator, EmailAddressValidator emailAddressValidator, PasswordValidator passwordValidator) {
        this.fullNameValidator = fullNameValidator;
        this.emailAddressValidator = emailAddressValidator;
        this.passwordValidator = passwordValidator;
    }

    @Override
    public void validate(User user, Errors errors) {
        fullNameValidator.validate(user.getFullName(), errors);
        emailAddressValidator.validate(user.getEmail(), errors);
        passwordValidator.validate(user.getPassword(), errors);
    }
}
