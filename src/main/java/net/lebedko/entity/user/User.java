package net.lebedko.entity.user;

import net.lebedko.entity.Entity;
import net.lebedko.entity.Validatable;
import net.lebedko.entity.general.EmailAddress;
import net.lebedko.entity.general.Password;

import java.util.Objects;

import static java.util.Objects.requireNonNull;


public class User implements Entity, Validatable {

    public enum UserRole {
        ADMIN, CLIENT
    }


    private int id;
    private FullName fullName;
    private EmailAddress email;
    private Password password;
    private UserRole role;
    private boolean activated;

    public User(FullName fullName, EmailAddress email, Password password, UserRole role) {
        this(0, fullName, email, password, role, false);
    }

    public User(int id, FullName fullName, EmailAddress email, Password password, UserRole role, boolean activated) {
        this.id = id;
        this.fullName = requireNonNull(fullName, "Full name cannot be null!");
        this.email = requireNonNull(email, "Email address cannot be null!");
        this.password = requireNonNull(password, "Password cannot be null!");
        this.role = requireNonNull(role, "Role cannot be null!");
        this.activated = activated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FullName getFullName() {
        return fullName;
    }

    public EmailAddress getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(fullName, user.getFullName()) &&
                Objects.equals(email, user.getEmail()) &&
                Objects.equals(password, user.getPassword()) &&
                Objects.equals(role, user.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, email, password, role);
    }


    @Override
    public boolean isValid() {
        return fullName.isValid() &&
                email.isValid() &&
                password.isValid();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName=" + fullName +
                ", email=" + email +
                ", password=" + password +
                ", role=" + role +
                '}';
    }
}
