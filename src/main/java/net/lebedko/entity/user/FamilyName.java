package net.lebedko.entity.user;

import net.lebedko.entity.Validatable;

import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static net.lebedko.util.Util.removeExtraSpaces;

/**
 * Created by alexandr.lebedko on 18.03.2017.
 */
public class FamilyName implements Validatable {

    private FirstName name;

    public FamilyName(String name) {
        requireNonNull(name, "Family Name cannot be null!");
        this.name = new FirstName(removeExtraSpaces(name));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FamilyName that = (FamilyName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


    @Override
    public boolean isValid() {
        return name.isValid();
    }

    @Override
    public String toString() {
        return name.toString();
    }

}
