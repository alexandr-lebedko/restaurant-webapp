package net.lebedko.entity.user;

import net.lebedko.entity.Validatable;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Created by alexandr.lebedko on 18.03.2017.
 */

public class FullName implements Validatable {

    private final FirstName firstName;
    private final LastName lastName;

    public FullName(FirstName firstName, LastName lastName) {
        requireNonNull(firstName, "First Name cannot be null");
        requireNonNull(lastName, "Last Name cannot be null");

        this.firstName = firstName;
        this.lastName = lastName;
    }

    public FirstName getFirstName() {
        return firstName;
    }

    public LastName getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullName fullName = (FullName) o;
        return Objects.equals(firstName, fullName.firstName) &&
                Objects.equals(lastName, fullName.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }


    @Override
    public boolean isValid() {
        return firstName.isValid() &&
                lastName.isValid();
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

}
