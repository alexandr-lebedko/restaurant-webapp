package net.lebedko.entity.menu;

import net.lebedko.entity.dish.Dish;
import net.lebedko.entity.dish.Dish.DishCategory;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * alexandr.lebedko : 22.03.2017.
 */
public class MenuTest {


    @Test(expected = NullPointerException.class)
    public void nullArgumentConstructorTest() {
        new Menu(null);
    }

    @Test
    public void nullArgumentAddMethodTest() {
        Menu menu = new Menu(new HashSet<>());
        menu.addMenuItem(null);

        assertEquals(0, menu.getMenuContent().size());
    }

    @Test
    public void regularArgumentAddMethodTest() {
        MenuItem menuItem0 = mock(MenuItem.class);
        MenuItem menuItem1 = mock(MenuItem.class);

        Menu menu = new Menu(new HashSet<>());
        menu.addMenuItem(menuItem0);
        menu.addMenuItem(menuItem1);

        assertEquals(2, menu.getMenuContent().size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void directAddToSetMethodTest() {
        MenuItem menuItem = mock(MenuItem.class);

        Menu menu = new Menu(new HashSet<>());

        menu.getMenuContent().add(menuItem);
    }

    @Test
    public void getByActivityMethodTest() {
        MenuItem activeItem0 = mock(MenuItem.class);
        MenuItem activeItem1 = mock(MenuItem.class);
        MenuItem notActiveItem = mock(MenuItem.class);

        when(activeItem0.isActive()).thenReturn(true);
        when(activeItem1.isActive()).thenReturn(true);
        when(notActiveItem.isActive()).thenReturn(false);

        Menu menu = new Menu(new HashSet<>(Arrays.asList(activeItem0, activeItem1, notActiveItem)));

        int activeItemsCount = menu.getByActivity(true).size();
        int notActiveItemsCount = menu.getByActivity(false).size();

        assertEquals(2, activeItemsCount);
        assertEquals(1, notActiveItemsCount);
    }

    @Test
    public void getByCategoryMethodTest() {
        Dish soup = mock(Dish.class);
        Dish dessert = mock(Dish.class);

        MenuItem soupCategoryMenuItem0 = mock(MenuItem.class);
        MenuItem soupCategoryMenuItem1 = mock(MenuItem.class);
        MenuItem dessertCategoryMenuItem = mock(MenuItem.class);

        when(soup.getCategory()).thenReturn(DishCategory.SOUP);
        when(dessert.getCategory()).thenReturn(DishCategory.DESSERT);

        when(soupCategoryMenuItem0.getDish()).thenReturn(soup);
        when(soupCategoryMenuItem1.getDish()).thenReturn(soup);
        when(dessertCategoryMenuItem.getDish()).thenReturn(dessert);

        Menu menu = new Menu(new HashSet<>(Arrays.asList(soupCategoryMenuItem0, soupCategoryMenuItem1, dessertCategoryMenuItem)));

        int soupCategoryItemCount = menu.getByDishCategory(DishCategory.SOUP).size();
        int dessertCategoryItemCount = menu.getByDishCategory(DishCategory.DESSERT).size();

        assertEquals(2, soupCategoryItemCount);
        assertEquals(1, dessertCategoryItemCount);
    }
}