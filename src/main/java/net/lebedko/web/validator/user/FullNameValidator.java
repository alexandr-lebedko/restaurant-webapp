package net.lebedko.web.validator.user;

import net.lebedko.entity.user.FullName;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.IValidator;

public class FullNameValidator implements IValidator<FullName> {
    private FirstNameValidator firstNameValidator;
    private LastNameValidator lastNameValidator;

    public FullNameValidator() {
        this(new FirstNameValidator(), new LastNameValidator());
    }

    public FullNameValidator(FirstNameValidator firstNameValidator, LastNameValidator lastNameValidator) {
        this.firstNameValidator = firstNameValidator;
        this.lastNameValidator = lastNameValidator;
    }

    @Override
    public void validate(FullName fullName, Errors errors) {
        firstNameValidator.validate(fullName.getFirstName(), errors);
        lastNameValidator.validate(fullName.getLastName(), errors);
    }
}
