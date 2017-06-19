package net.lebedko.entity.user;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by alexandr.lebedko on 18.03.2017.
 */


public class FamilyNameTest {


    @Test(expected = NullPointerException.class)
    public void nullArgumentTest() {
        new LastName(null);
    }

    @Test
    public void withDigitsArgumentTest() {
        String digits = "6484";
        LastName familyName = new LastName(digits);

        assertFalse(familyName.isValid());
    }

    @Test
    public void withEmptyStringArgumentTest() {
        String empty = "";
        LastName familyName = new LastName(empty);

        assertFalse(familyName.isValid());
    }

    @Test
    public void withStringWithSpacesArgumentTest() {
        String spaces = "     ";
        LastName familyName = new LastName(spaces);

        assertFalse(familyName.isValid());
    }

    @Test
    public void withDigitsAndLettersArgumentTest() {
        String invalid = "123ada A";
        LastName familyName = new LastName(invalid);

        assertFalse(familyName.isValid());
    }

    @Test
    public void differentLanguagesArgumentTest() {
        String invalid = "Zs Яы";
        LastName familyName = new LastName(invalid);

        assertFalse(familyName.isValid());
    }

    @Test
    public void longNameArgumentTest() {
        String longFamilyName = "VeryVeryLooooongFamilyName";
        LastName familyName = new LastName(longFamilyName);

        assertFalse(familyName.isValid());
    }

    @Test
    public void extraSpacesTrimmedOfTest() {
        String familyNameWithExtraSpaces = "Mac           Gregor";
        String trimmedOfFamilyName = "Mac Gregor";

        LastName familyName = new LastName(familyNameWithExtraSpaces);

        assertEquals(trimmedOfFamilyName, familyName.toString());

    }

    @Test
    public void apostrophesArgumentTest() {
        String apostrophes = "'''";
        LastName familyName = new LastName(apostrophes);

        assertFalse(familyName.isValid());

    }

    @Test
    public void symbolsArgumentTest() {
        String symbols = "@#$%^*/-";
        LastName familyName = new LastName(symbols);

        assertFalse(familyName.isValid());

    }

    @Test
    public void oneWordFamilyNameTest() {
        String familyNameString = "Lebedko";
        LastName familyName = new LastName(familyNameString);

        assertTrue(familyName.isValid());
    }

    @Test
    public void twoWordsArgumentTest() {
        String familyNameString = "Van Buren";
        LastName familyName = new LastName(familyNameString);

        assertTrue(familyName.isValid());

    }

    @Test
    public void threeWordArgumentTest() {
        String familyNameString = "Lon Chaney III";
        LastName familyName = new LastName(familyNameString);

        assertTrue(familyName.isValid());
    }

    @Test
    public void prefixedFamilyName() {
        String familyNameString = "O'Neal";
        LastName familyName = new LastName(familyNameString);

        assertTrue(familyName.isValid());
    }

}