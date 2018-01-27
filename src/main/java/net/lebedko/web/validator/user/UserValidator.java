package net.lebedko.web.validator.user;

import net.lebedko.entity.user.User;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.Validator;

public class UserValidator implements Validator<User> {
    private FullNameValidator fullNameValidator;
    private EmailAddressValidator emailAddressValidator;
    private PasswordValidator passwordValidator;

    public UserValidator() {
        this.fullNameValidator = new FullNameValidator();
        this.emailAddressValidator =  new EmailAddressValidator();
        this.passwordValidator = new PasswordValidator();
    }

    @Override
    public void validate(User user, Errors errors) {
        fullNameValidator.validate(user.getFullName(), errors);
        emailAddressValidator.validate(user.getEmail(), errors);
        passwordValidator.validate(user.getPassword(), errors);
    }
}
