package net.lebedko.entity.user;

import java.util.Objects;

import static net.lebedko.entity.general.StringI18N.removeExtraSpaces;

public class FirstName {

    private final String name;

    public FirstName(String name) {
        this.name = removeExtraSpaces(name).trim();
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
    public String toString() {
        return name;
    }
}