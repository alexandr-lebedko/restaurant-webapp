package net.lebedko.entity.user;

import net.lebedko.entity.Validatable;

import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static net.lebedko.util.Util.removeExtraSpaces;

/**
 * Created by alexandr.lebedko on 18.03.2017.
 */
public class LastName implements Validatable {

    private FirstName name;

    public LastName(String name) {
        requireNonNull(name, "Last Name cannot be null!");
        this.name = new FirstName(removeExtraSpaces(name));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LastName that = (LastName) o;
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
