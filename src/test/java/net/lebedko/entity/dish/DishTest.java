package net.lebedko.entity.dish;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import net.lebedko.entity.general.Price;
import net.lebedko.entity.general.Text;
import net.lebedko.entity.general.Title;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import static net.lebedko.entity.dish.Dish.DishCategory;

/**
 * alexandr.lebedko : 22.03.2017.
 */

public class DishTest {
    private static Price validPrice;
    private static Price invalidPrice;
    private static Title validTitle;
    private static Title invalidTitle;
    private static Text validDescription;
    private static Text invalidDescription;
    private static DishCategory dishType = DishCategory.SOUP;

    @BeforeClass
    public static void startUp() {
            validPrice = mock(Price.class);
            invalidPrice = mock(Price.class);
            invalidTitle = mock(Title.class);
            validTitle = mock(Title.class);
            validDescription = mock(Text.class);
            invalidDescription = mock(Text.class);

            when(validPrice.isValid()).thenReturn(true);
            when(invalidPrice.isValid()).thenReturn(false);
            when(validTitle.isValid()).thenReturn(true);
            when(invalidTitle.isValid()).thenReturn(false);
            when(validDescription.isValid()).thenReturn(true);
            when(invalidDescription.isValid()).thenReturn(false);

    }


    @Test(expected = NullPointerException.class)
    public void nullArgumentsTest() {
        new Dish(null,  null, null);
    }

    @Test
    public void invalidTitleArgumentsValidityTest() {
        assertFalse(new Dish(invalidTitle, validDescription, dishType).isValid());
    }
    @Test
    public void invalidDescriptionValidityTest() {
        assertFalse(new Dish(validTitle, invalidDescription,  dishType).isValid());
    }

    @Test
    public void validArgumentsValidityTest(){
        assertTrue(new Dish(validTitle,validDescription,dishType).isValid());
    }

}