package net.lebedko.web.validator.user;

import net.lebedko.entity.user.FullName;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.IValidator;

import static java.util.Objects.requireNonNull;

/**
 * alexandr.lebedko : 19.06.2017
 */
public class FullNameValidator implements IValidator<FullName> {
    private FirstNameValidator firstNameValidator;
    private LastNameValidator lastNameValidator;

    public FullNameValidator() {
        this(new FirstNameValidator(), new LastNameValidator());
    }

    public FullNameValidator(FirstNameValidator firstNameValidator, LastNameValidator lastNameValidator) {
        this.firstNameValidator = requireNonNull(firstNameValidator, "FirstNameValidator is required and cannot be null!");
        this.lastNameValidator = requireNonNull(lastNameValidator, "LastNameValidator is required and cannot be null!");
    }

    @Override
    public void validate(FullName fullName, Errors errors) {
        firstNameValidator.validate(fullName.getFirstName(), errors);
        lastNameValidator.validate(fullName.getLastName(), errors);
    }
}
