package net.lebedko.entity.general;

import net.lebedko.entity.Validatable;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * alexandr.lebedko : 21.03.2017.
 */

public class Title implements Validatable {
    public static final int MIN_TITLE_LENGTH = 2;
    public static final int MAX_TITLE_LENGTH = 50;

    private final String titleString;

    public Title(String titleString) {
        requireNonNull(titleString, "Argument cannot be null");

        this.titleString = titleString.replaceAll("[ ]{2,}", "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Title title = (Title) o;
        return Objects.equals(titleString, title.titleString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titleString);
    }

    @Override
    public boolean isValid() {
        return
                titleString.length() >= MIN_TITLE_LENGTH &&
                        titleString.length() <= MAX_TITLE_LENGTH;
    }

    @Override
    public String toString() {
        return titleString;
    }

}
