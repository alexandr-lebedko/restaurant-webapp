package net.lebedko.entity.user;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Created by alexandr.lebedko on 20.03.2017.
 */

public class PasswordFromHashTest {

    private static final String passwordString = "12345678";
    private static final String hashedPassword = "25d55ad283aa40af464c76d713c7ad";


    @Test(expected = NullPointerException.class)
    public void nullArgumentStringTest() {
        Password.createPasswordFromHash(null);
    }

    @Test
    public void getPasswordHashTest() {
        Password passwordFromHash = Password.createPasswordFromHash(hashedPassword);

        assertEquals(hashedPassword, passwordFromHash.getPasswordHash());
    }

    @Test
    public void equalsPasswordFromHashWithPasswordFromStringTest() {
        Password passwordFromString = Password.createPasswordFromString(passwordString);
        Password passwordFromHash = Password.createPasswordFromHash(hashedPassword);

        assertEquals(passwordFromHash, passwordFromString);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void isValidMethodTest() {
        Password passwordFromHash = Password.createPasswordFromHash(hashedPassword);
        passwordFromHash.isValid();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getPasswordStringTest() {
        Password passwordFromHash = Password.createPasswordFromHash(hashedPassword);
        passwordFromHash.getPasswordString();
    }

}
