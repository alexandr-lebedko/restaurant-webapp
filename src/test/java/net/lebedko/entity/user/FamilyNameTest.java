package net.lebedko.entity.user;

import net.lebedko.entity.user.FamilyName;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by alexandr.lebedko on 18.03.2017.
 */


public class FamilyNameTest {


    @Test(expected = NullPointerException.class)
    public void nullArgumentTest() {
        new FamilyName(null);
    }

    @Test
    public void withDigitsArgumentTest() {
        String digits = "6484";
        FamilyName familyName = new FamilyName(digits);

        assertFalse(familyName.isValid());
    }

    @Test
    public void withEmptyStringArgumentTest() {
        String empty = "";
        FamilyName familyName = new FamilyName(empty);

        assertFalse(familyName.isValid());
    }

    @Test
    public void withStringWithSpacesArgumentTest() {
        String spaces = "     ";
        FamilyName familyName = new FamilyName(spaces);

        assertFalse(familyName.isValid());
    }

    @Test
    public void withDigitsAndLettersArgumentTest() {
        String invalid = "123ada A";
        FamilyName familyName = new FamilyName(invalid);

        assertFalse(familyName.isValid());
    }

    @Test
    public void differentLanguagesArgumentTest() {
        String invalid = "Zs Яы";
        FamilyName familyName = new FamilyName(invalid);

        assertFalse(familyName.isValid());
    }

    @Test
    public void longNameArgumentTest() {
        String longFamilyName = "VeryVeryLooooongFamilyName";
        FamilyName familyName = new FamilyName(longFamilyName);

        assertFalse(familyName.isValid());
    }

    @Test
    public void extraSpacesTrimmedOfTest() {
        String familyNameWithExtraSpaces = "Mac           Gregor";
        String trimmedOfFamilyName = "Mac Gregor";

        FamilyName familyName = new FamilyName(familyNameWithExtraSpaces);

        assertEquals(trimmedOfFamilyName, familyName.toString());

    }

    @Test
    public void apostrophesArgumentTest() {
        String apostrophes = "'''";
        FamilyName familyName = new FamilyName(apostrophes);

        assertFalse(familyName.isValid());

    }

    @Test
    public void symbolsArgumentTest() {
        String symbols = "@#$%^*/-";
        FamilyName familyName = new FamilyName(symbols);

        assertFalse(familyName.isValid());

    }

    @Test
    public void oneWordFamilyNameTest() {
        String familyNameString = "Lebedko";
        FamilyName familyName = new FamilyName(familyNameString);

        assertTrue(familyName.isValid());
    }

    @Test
    public void twoWordsArgumentTest() {
        String familyNameString = "Van Buren";
        FamilyName familyName = new FamilyName(familyNameString);

        assertTrue(familyName.isValid());

    }

    @Test
    public void threeWordArgumentTest() {
        String familyNameString = "Lon Chaney III";
        FamilyName familyName = new FamilyName(familyNameString);

        assertTrue(familyName.isValid());
    }

    @Test
    public void prefixedFamilyName() {
        String familyNameString = "O'Neal";
        FamilyName familyName = new FamilyName(familyNameString);

        assertTrue(familyName.isValid());
    }

}