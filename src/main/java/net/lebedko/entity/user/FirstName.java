package net.lebedko.entity.user;

import net.lebedko.entity.Validatable;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;
import static net.lebedko.util.Util.removeExtraSpaces;


/**
 * Created by alexandr.lebedko on 18.03.2017.
 */
public class FirstName implements Validatable {
    private static final int max_length = 20;
    private static final Pattern pattern = Pattern.compile("([a-zA-Z]+([- ']?[a-zA-Z]+)+)|([а-яА-Я]+([- ']?[а-яА-Я]+)+)");

    private final Matcher matcher;
    private final String name;

    public FirstName(String name) {
        requireNonNull(name, "Name cannot be null");
        this.name = removeExtraSpaces(name).trim();
        this.matcher = pattern.matcher(this.name);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FirstName firstName = (FirstName) o;
        return Objects.equals(name, firstName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean isValid() {
        return matcher.matches() &&
                name.length() <= max_length &&
                name.trim().length() > 0;
    }


    @Override
    public String toString() {
        return name;
    }


}
