package net.lebedko.entity.user;

import net.lebedko.entity.Validatable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static java.util.Objects.nonNull;
import static net.lebedko.util.Util.removeExtraSpaces;

/**
 * Created by alexandr.lebedko on 18.03.2017.
 */
public abstract class Password implements Validatable {
    protected static final int min_length= 8;

    String passwordString;
    String passwordHash;


    public abstract String getPasswordString();

    public abstract String getPasswordHash();

    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Password)) return false;

        Password other = (Password) o;

        return Objects.equals(this.getPasswordHash(), other.getPasswordHash());
    }

    public String toString() {
        return "Password{ Hash=" + getPasswordHash() + "}";
    }

    public static Password createPasswordFromString(String password) {
        return new PasswordImpl(password);
    }

    public static Password createPasswordFromHash(String passwordHash) {
        return new HashedPassword(passwordHash);
    }

    private static class PasswordImpl extends Password {
      protected static final String MD_ALGORITHM = "MD5";


        private PasswordImpl(String passwordString) {
            requireNonNull(passwordString, "Argument cannot be null");
            this.passwordString =removeExtraSpaces(passwordString).trim();
        }

        @Override
        public boolean isValid() {
            return passwordString.length() >= min_length;
        }

        @Override
        public String getPasswordString() {
            return passwordString;
        }

        @Override
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
                /*NOP*/
            }
            return passwordHash;
        }

    }

    private static class HashedPassword extends Password {

        private HashedPassword(String passwordHash) {
            requireNonNull(passwordHash, "Argument cannot be null");

            this.passwordHash = passwordHash;
        }

        @Override
        public String getPasswordHash() {
            return passwordHash;
        }

        @Override
        public String getPasswordString() {
            throw new UnsupportedOperationException("Cannot convert hashed string to password string ");
        }

        @Override
        public boolean isValid() {
            throw new UnsupportedOperationException("Cannot define validity of hashed string");
        }

    }

}
