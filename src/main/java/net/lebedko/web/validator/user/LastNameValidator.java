package net.lebedko.web.validator.user;

import net.lebedko.entity.user.LastName;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LastNameValidator implements Validator<LastName> {
    private static final Pattern PATTERN = Pattern.compile("([a-zA-Z]+([- ']?[a-zA-Z]+)+)|([а-яА-Я]+([- ']?[а-яА-Я]+)+)");
    private static final int MAX_LENGTH = 20;

    @Override
    public void validate(LastName familyName, Errors errors) {
        Matcher matcher = PATTERN.matcher(familyName.toString());
        if (!matcher.matches() || familyName.toString().length() > MAX_LENGTH) {
            errors.register("lastName", "page.error.lastName");
        }
    }
}