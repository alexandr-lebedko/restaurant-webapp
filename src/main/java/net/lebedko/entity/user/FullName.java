package net.lebedko.entity.user;

import net.lebedko.entity.Validatable;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Created by alexandr.lebedko on 18.03.2017.
 */

public class FullName implements Validatable {

    private final FirstName firstName;
    private final FamilyName familyName;

    public FullName(FirstName firstName, FamilyName familyName) {
        requireNonNull(firstName, "First Name cannot be null");
        requireNonNull(familyName, "Family Name cannot be null");

        this.firstName = firstName;
        this.familyName = familyName;
    }

    public FirstName getFirstName() {
        return firstName;
    }

    public FamilyName getFamilyName() {
        return familyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullName fullName = (FullName) o;
        return Objects.equals(firstName, fullName.firstName) &&
                Objects.equals(familyName, fullName.familyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, familyName);
    }


    @Override
    public boolean isValid() {
        return firstName.isValid() &&
                familyName.isValid();
    }

    @Override
    public String toString() {
        return "FullName{" +
                "firstName=" + firstName +
                ", familyName=" + familyName +
                '}';
    }

}
