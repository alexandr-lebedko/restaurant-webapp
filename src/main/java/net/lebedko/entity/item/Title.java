package net.lebedko.entity.item;

import net.lebedko.entity.general.StringI18N;

import java.util.Objects;

import static net.lebedko.entity.general.StringI18N.removeExtraSpaces;

public class Title {
    private StringI18N value;

    public Title(StringI18N value) {
        this.value = removeExtraSpaces(value);
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
