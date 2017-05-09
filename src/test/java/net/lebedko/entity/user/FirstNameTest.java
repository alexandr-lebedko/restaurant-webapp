package net.lebedko.entity.user;

import net.lebedko.entity.user.FirstName;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by alexandr.lebedko on 19.03.2017.
 */
public class FirstNameTest {


    @Test(expected = NullPointerException.class)
    public void nullArgumentTest() {
        new FirstName(null);
    }

    @Test
    public void emptyStringArgumentTest() {
        String emptyString = "";
        FirstName firstName = new FirstName(emptyString);

        assertFalse(firstName.isValid());
    }

    @Test
    public void stringWithSpacesArgumentTest() {
        String spaces = "     ";
        FirstName firstName = new FirstName(spaces);

        assertFalse(firstName.isValid());
    }

    @Test
    public void symbolsStringArgumentTest() {
        String symbols = "!@@$$%^&*(";
        FirstName firstName = new FirstName(symbols);

        assertFalse(firstName.isValid());
    }

    @Test
    public void stringWithDigitsArgumentTest() {
        String digits = "123454523";
        FirstName firstName = new FirstName(digits);

        assertFalse(firstName.isValid());
    }

    @Test
    public void validNameArgumentTest() {
        String nameString = "Александр";
        FirstName firstName = new FirstName(nameString);

        assertTrue(firstName.isValid());
    }

    @Test
    public void twoWordsNameArgumentTest() {
        String name = "Jozef-Schmozev";
        FirstName firstName = new FirstName(name);

        assertTrue(firstName.isValid());
    }

    @Test
    public void prefixedNameArgumentTest() {
        String name = "d'Are";
        FirstName firstName = new FirstName(name);

        assertTrue(firstName.isValid());
    }

    @Test
    public void tooLongArgumentTest() {
        String longName = "Very Loooooooong Name";
        FirstName firstName = new FirstName(longName);

        assertFalse(firstName.isValid());
    }

    @Test
    public void tooShortArgumentTest(){
        String shortName = "X";
        FirstName firstName = new FirstName(shortName);

        assertFalse(firstName.isValid());
    }
}