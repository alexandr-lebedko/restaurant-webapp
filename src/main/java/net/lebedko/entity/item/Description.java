package net.lebedko.entity.item;

import net.lebedko.entity.Validatable;

import java.util.Objects;

import static net.lebedko.util.Util.removeExtraSpaces;

/**
 * alexandr.lebedko : 30.07.2017.
 */
public class Description implements Validatable {
    private static final int MAX_LENGTH = 255;
    private static final int MIN_LENGTH = 70;

    private String value;

    public Description(String value) {
        this.value = removeExtraSpaces(value);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Description that = (Description) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean isValid() {
        return (value.length() >= MIN_LENGTH) && (value.length() <= MAX_LENGTH);
    }

    @Override
    public String toString() {
        return value;
    }
}
