package net.lebedko.entity.user;

import net.lebedko.entity.Validatable;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

/**
 * alexandr.lebedko on 18.03.2017.
 */

public class EmailAddress implements Validatable {
    public static final Pattern pattern = Pattern.compile("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
    public static final int max_length = 30;

    private final String address;
    private final Matcher matcher;


    public EmailAddress(String address) {
        requireNonNull(address, "Address cannot be null");

        this.address = address.trim();
        this.matcher = pattern.matcher(this.address);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailAddress email = (EmailAddress) o;
        return Objects.equals(address, email.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }

    @Override
    public boolean isValid() {
        return matcher.matches() &&
                address.length() <= max_length;
    }

    @Override
    public String toString() {
        return  address;
    }

}
