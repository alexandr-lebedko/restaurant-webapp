package net.lebedko.entity.user;

import net.lebedko.entity.general.StringI18N;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

public class Password {
    private static final Logger LOG = LogManager.getLogger();
    private static final String MD_ALGORITHM = "MD5";

    private String passwordString;
    private String passwordHash;

    private Password(String passwordString, String passwordHash) {
        this.passwordString = ofNullable(passwordString)
                .map(StringI18N::removeExtraSpaces)
                .map(String::trim)
                .orElse(null);

        this.passwordHash = passwordHash;
    }

    public String getPasswordString() {
        return passwordString;
    }

    public String getPasswordHash() {
        if (nonNull(passwordHash)) {
            return passwordHash;
        }
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance(MD_ALGORITHM);
            md.update(passwordString.getBytes());
            byte[] bytes = md.digest();
            for (byte b : bytes) {
                sb.append(Integer.toHexString(b & 255));
            }
            passwordHash = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            LOG.error(e);
        }
        return passwordHash;
    }

    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Password)) return false;

        Password other = (Password) o;
        return Objects.equals(this.getPasswordHash(), other.getPasswordHash());
    }

    @Override
    public int hashCode() {
        return Objects.hash(passwordHash);
    }

    public String toString() {
        return "Password{String=" + passwordString + ", Hash=" + getPasswordHash() + "}";
    }

    public static Password createPasswordFromString(String password) {
        return new Password(password, null);
    }

    public static Password createPasswordFromHash(String passwordHash) {
        return new Password(null, passwordHash);
    }
}
