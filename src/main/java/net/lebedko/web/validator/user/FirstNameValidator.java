package net.lebedko.web.validator.user;

import net.lebedko.entity.user.FirstName;
import net.lebedko.web.validator.Errors;
import net.lebedko.web.validator.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FirstNameValidator implements Validator<FirstName> {
    private static final Pattern PATTERN = Pattern.compile("([a-zA-Z]+([- ']?[a-zA-Z]+)+)|([а-яА-Я]+([- ']?[а-яА-Я]+)+)");
    private static final int MAX_LENGTH = 20;

    @Override
    public void validate(FirstName firstName, Errors errors) {
        Matcher matcher = PATTERN.matcher(firstName.toString());
        if (!matcher.matches() || firstName.toString().length() > MAX_LENGTH) {
            errors.register("firstName", "page.error.firstName");
        }
    }
}