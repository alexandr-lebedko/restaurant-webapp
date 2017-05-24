package net.lebedko.entity.user;

import net.lebedko.entity.general.Password;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by alexandr.lebedko on 20.03.2017.
 */

public class PasswordFromStringTest {

    @Test(expected = NullPointerException.class)
    public void nullPasswordStringTest() {
        Password.createPasswordFromString(null);
    }

    @Test
    public void smallPasswordStringArgumentValidityTest() {
        String smallPasswordString = "abc";
        Password passwordFromString = Password.createPasswordFromString(smallPasswordString);

        assertFalse(passwordFromString.isValid());
    }

    @Test
    public void validArgumentStringValidityTest() {
        String validPasswordString = "12345678";
        Password passwordFromString = Password.createPasswordFromString(validPasswordString);

        assertTrue(passwordFromString.isValid());
    }

    @Test
    public void encodingPasswordTest(){
        String validPasswordString = "12345678";
        Password passwordFromString = Password.createPasswordFromString(validPasswordString);

        assertNotEquals(validPasswordString, passwordFromString.getPasswordHash());
    }




}