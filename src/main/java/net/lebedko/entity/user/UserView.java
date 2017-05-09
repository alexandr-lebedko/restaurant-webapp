package net.lebedko.entity.user;

/**
 * alexandr.lebedko : 08.05.2017.
 */

public class UserView {
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
}
