package net.lebedko.web.validator.user;

import net.lebedko.entity.user.FullName;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.Validator;

public class FullNameValidator implements Validator<FullName> {
    private FirstNameValidator firstNameValidator;
    private LastNameValidator lastNameValidator;

    public FullNameValidator() {
        this.firstNameValidator = new FirstNameValidator();
        this.lastNameValidator = new LastNameValidator();
    }

    @Override
    public void validate(FullName fullName, Errors errors) {
        firstNameValidator.validate(fullName.getFirstName(), errors);
        lastNameValidator.validate(fullName.getLastName(), errors);
    }
}