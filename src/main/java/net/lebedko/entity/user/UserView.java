package net.lebedko.entity.user;

import net.lebedko.entity.Validatable;

import static java.util.Objects.nonNull;

/**
 * alexandr.lebedko : 08.05.2017.
 */

public class UserView implements Validatable {
    private EmailAddress emailAddress;
    private Password password;

    public UserView(EmailAddress emailAddress, Password password) {
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    @Override
    public boolean isValid() {
        return nonNull(emailAddress)
                && nonNull(password)
                && emailAddress.isValid()
                && password.isValid();
    }

    @Override
    public String toString() {
        return "UserView{" +
                "emailAddress=" + emailAddress +
                ", password=" + password +
                '}';
    }
}
