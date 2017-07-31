package net.lebedko.entity.item;

import net.lebedko.entity.Validatable;

import java.util.Objects;

import static net.lebedko.util.Util.*;

/**
 * alexandr.lebedko : 30.07.2017.
 */

public class Title implements Validatable {

    private static final int MIN_TITLE_LENGTH = 2;
    private static final int MAX_TITLE_LENGTH = 50;

    private final String value;

    public Title(String value) {
        this.value = removeExtraSpaces(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Title title = (Title) o;

        return Objects.equals(value, title.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(value);
    }

    @Override
    public boolean isValid() {

        return (value.length() >= MIN_TITLE_LENGTH) && (value.length() <= MAX_TITLE_LENGTH);
    }

    @Override
    public String toString() {
        return value;
    }

}
