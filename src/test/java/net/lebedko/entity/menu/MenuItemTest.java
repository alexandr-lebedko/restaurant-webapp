package net.lebedko.entity.menu;

import net.lebedko.entity.dish.Dish;
import net.lebedko.entity.general.Price;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * alexandr.lebedko : 22.03.2017.
 */
public class MenuItemTest {

    private static boolean setUpIsDone = false;

    private static  Dish validDish;
    private static Dish invalidDish;
    private static Price validPrice;
    private static Price invalidPrice;

    @BeforeClass
    public static void setUp() {

        validDish = mock(Dish.class);
        invalidDish = mock(Dish.class);

        validPrice = mock(Price.class);
        invalidPrice = mock(Price.class);

        when(validDish.isValid()).thenReturn(true);
        when(invalidDish.isValid()).thenReturn(false);

        when(validPrice.isValid()).thenReturn(true);
        when(invalidPrice.isValid()).thenReturn(false);

    }

    @Test(expected = NullPointerException.class)
    public void nullArgumentTest() {
        new MenuItem(null, null, false);
    }

    @Test
    public void invalidDishArgumentValidityTest() {
        assertFalse(new MenuItem(invalidDish, validPrice, true).isValid());
    }

    @Test
    public void validArgumentValidityTest() {
        assertTrue(new MenuItem(validDish, validPrice, true).isValid());
    }

    @Test
    public void invalidPriceValidityTest() {
        assertFalse(new MenuItem(validDish, invalidPrice, false).isValid());
    }


}