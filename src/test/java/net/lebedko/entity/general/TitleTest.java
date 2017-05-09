package net.lebedko.entity.general;

import net.lebedko.entity.general.Title;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * alexandr.lebedko : 22.03.2017.
 */
public class TitleTest {

    @Test(expected = NullPointerException.class)
    public void nullArgumentTest() {
        new Title(null);
    }

    @Test
    public void emptyStringArgumentValidityTest() {
        String empty = "";
        Title title = new Title(empty);

        assertFalse(title.isValid());
    }

    @Test
    public void allSpacesStringArgumentValidityTest() {
        String spaces = "         ";
        Title title = new Title(spaces);

        assertFalse(title.isValid());
    }

    @Test
    public void tooShortStringArgumentValidityTest() {
        String titleString = "A";
        Title title = new Title(titleString);

        assertFalse(title.isValid());
    }

    @Test
    public void tooLongStringArgumentTest() throws Exception {
        String titleString = "123456789012345678901234567890123456789012345678901";

        assertFalse(new Title(titleString).isValid());
    }

    @Test
    public void regularStringArgumentValidityTest() throws Exception {
        String titleString = "Spaghetti alla Bolognese";

        assertTrue(new Title(titleString).isValid());
    }


}