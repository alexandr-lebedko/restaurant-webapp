package net.lebedko.entity.item;

import net.lebedko.entity.Validatable;
import net.lebedko.entity.general.StringI18N;

import java.util.Objects;

import static net.lebedko.util.Util.*;

/**
 * alexandr.lebedko : 30.07.2017.
 */

public class Title implements Validatable {

    private static final int MIN_TITLE_LENGTH = 2;
    private static final int MAX_TITLE_LENGTH = 50;

    private StringI18N value;

    public Title(StringI18N value) {
        this.value = removeExtraSpaces(value);
    }

    @Override
    public boolean isValid() {
        return value.isValid() && isValidStringValues();
    }

    private boolean isValidStringValues() {
        return this.value.getMap().entrySet().stream()
                .allMatch(entry ->
                        isInRange(entry.getValue().length(), MIN_TITLE_LENGTH, MAX_TITLE_LENGTH));
    }

    public StringI18N getValue() {
        return value;
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
    public String toString() {
        return "Title{" +
                "value=" + value +
                '}';
    }
}
