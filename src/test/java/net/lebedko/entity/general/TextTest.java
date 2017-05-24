package net.lebedko.entity.general;

import org.junit.Test;

/**
 * alexandr.lebedko : 22.03.2017.
 */
public class TextTest {

    @Test(expected = NullPointerException.class)
    public void nullArgumentTest() {
        new Text(null);
    }

}