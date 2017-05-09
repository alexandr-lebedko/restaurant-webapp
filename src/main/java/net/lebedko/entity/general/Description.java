package net.lebedko.entity.general;

import net.lebedko.entity.Validatable;

import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static net.lebedko.util.Util.removeExtraSpaces;

/**
 * alexandr.lebedko : 21.03.2017.
 */
public class Description implements Validatable {
    public final static int MAX_TEXT_LENGTH = 520;

    private final String textString;

    public Description(String textString) {
        requireNonNull(textString, "Argument cannot be null");
        this.textString = removeExtraSpaces(textString);
    }

    @Override
    public boolean isValid() {
        return textString.length() <= MAX_TEXT_LENGTH;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Description text = (Description) o;
        return Objects.equals(textString, text.textString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textString);
    }

    @Override
    public String toString() {
        return textString;
    }
}
