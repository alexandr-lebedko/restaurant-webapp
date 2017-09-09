package net.lebedko.entity.user;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by alexandr.lebedko on 20.03.2017.
 */
public class EmailTest {

    @Test(expected = NullPointerException.class)
    public void nullArgumentTest() {
        new EmailAddress(null);
    }

    @Test
    public void emptyEmailStringArgumentTest() {
        String emptyString = "";
        EmailAddress email = new EmailAddress(emptyString);

        assertFalse(email.isValid());
    }

    @Test
    public void emailStringWithOnlySpacesArgumentTest() {
        String spaces = "          ";
        EmailAddress email = new EmailAddress(spaces);

        assertFalse(email.isValid());
    }

    @Test
    public void malformatedEmailStringArgumentTest() {
        String invalidEmailString = "aaa@@";
        EmailAddress email = new EmailAddress(invalidEmailString);

        assertFalse(email.isValid());

        invalidEmailString = "aaa@.com";
        email = new EmailAddress(invalidEmailString);

        assertFalse(email.isValid());

        invalidEmailString = "@aaa.com";
        email = new EmailAddress(invalidEmailString);

        assertFalse(email.isValid());

        invalidEmailString = "aaa.com";
        email = new EmailAddress(invalidEmailString);

        assertFalse(email.isValid());
    }

    @Test
    public void tooLongEmailStringArgumentTest() {
        String longEmailString = "somebody.long.email@example.com";
        EmailAddress email = new EmailAddress(longEmailString);

        assertFalse(email.isValid());
    }

    @Test
    public void typicalEmailStringArgumentTest() {
        String emailString = "something@something.com";
        EmailAddress email = new EmailAddress(emailString);

        assertTrue(email.isValid());

        emailString = "something123@something.com";
        email = new EmailAddress(emailString);

        assertTrue(email.isValid());

        emailString = "word.anotherword@something.com";
        email = new EmailAddress(emailString);

        assertTrue(email.isValid());

    }

}