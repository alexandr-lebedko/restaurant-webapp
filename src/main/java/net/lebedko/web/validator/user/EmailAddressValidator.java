package net.lebedko.web.validator.user;

import net.lebedko.entity.user.EmailAddress;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.IValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailAddressValidator implements IValidator<EmailAddress> {
    private static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
    private static final int MAX_LENGTH = 30;

    @Override
    public void validate(EmailAddress emailAddress, Errors errors) {
        Matcher matcher = PATTERN.matcher(emailAddress.toString());
        if (!matcher.matches() || emailAddress.toString().length() > MAX_LENGTH) {
            errors.register("email", "page.error.email");
        }
    }
}
