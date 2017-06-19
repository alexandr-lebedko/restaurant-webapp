package net.lebedko.entity.user;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * alexandr.lebedko : 19.03.2017.
 */
public class FullNameTest {
    private static boolean setUpIsDone = false;

    private static FirstName validFirstName;
    private static FirstName invalidFirstName;
    private static LastName validFamilyName;
    private static LastName invalidFamilyName;

    @Before
    public void setUp() {
        if (!setUpIsDone) {
            validFamilyName = mock(LastName.class);
            invalidFamilyName = mock(LastName.class);
            validFirstName = mock(FirstName.class);
            invalidFirstName = mock(FirstName.class);

            when(validFamilyName.isValid()).thenReturn(true);
            when(invalidFamilyName.isValid()).thenReturn(false);
            when(validFirstName.isValid()).thenReturn(true);
            when(invalidFirstName.isValid()).thenReturn(false);

            setUpIsDone = true;
        }
    }

    @Test(expected = NullPointerException.class)
    public void firstNameNullTest() {
        new FullName(null, null);
    }

    @Test
    public void invalidFirstNameTest() {
        assertFalse(new FullName(invalidFirstName, validFamilyName).isValid());
    }

    @Test
    public void invalidFamilyNameTest() {
        assertFalse(new FullName(validFirstName, invalidFamilyName).isValid());
    }

    @Test
    public void validArgumentsTest() {
        assertTrue(new FullName(validFirstName, validFamilyName).isValid());
    }

}