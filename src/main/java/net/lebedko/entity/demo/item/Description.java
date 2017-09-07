package net.lebedko.entity.demo.item;

import net.lebedko.entity.Validatable;
import net.lebedko.entity.demo.general.StringI18N;

import java.util.Objects;

import static net.lebedko.util.Util.isInRange;
import static net.lebedko.util.Util.removeExtraSpaces;

/**
 * alexandr.lebedko : 30.07.2017.
 */
public class Description implements Validatable {
    private static final int MAX_LENGTH = 255;
    private static final int MIN_LENGTH = 70;

    private StringI18N value;

    public Description(StringI18N value) {
        this.value = removeExtraSpaces(value);
    }


    @Override
    public boolean isValid() {
        return value.isValid() && isValidStringValues();
    }

    private boolean isValidStringValues() {
        return this.value.getMap().entrySet().stream()
                .allMatch(entry ->
                        isInRange(entry.getValue().length(), MIN_LENGTH, MAX_LENGTH));
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

        Description that = (Description) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }


    @Override
    public String toString() {
        return value.toString();
    }
}
