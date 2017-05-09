package net.lebedko.entity.order;

import net.lebedko.entity.general.Price;
import net.lebedko.entity.menu.MenuItem;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * alexandr.lebedko : 23.03.2017.
 */
public class OrderItemTest {

    @Test(expected = NullPointerException.class)
    public void nullArgumentsTest() {
        new OrderItem(null, null, 0, false);
    }

    @Test
    public void invalidArgumentsValidityTest() {
        MenuItem menuItem = mock(MenuItem.class);
        when(menuItem.isValid()).thenReturn(false);

        Order order = mock(Order.class);
        when(order.isValid()).thenReturn(false);

        OrderItem orderItem = new OrderItem(order, menuItem, -1, false);

        assertFalse("Order Item should be invalid!", orderItem.isValid());
    }

    @Test
    public void validArgumentsValidityTest() {
        MenuItem menuItem = mock(MenuItem.class);
        when(menuItem.isValid()).thenReturn(true);

        Order order = mock(Order.class);
        when(order.isValid()).thenReturn(true);

        OrderItem orderItem = new OrderItem(order, menuItem, 3, false);

        assertTrue("Order Item should be valid!", orderItem.isValid());
    }


    @Test
    public void calculatePriceTest() {
        Price price = new Price(123.10);
        int amount = 3;

        Price multipliedPrice = price.multiplyBy(amount);

        MenuItem menuItem = mock(MenuItem.class);
        when(menuItem.getPrice()).thenReturn(price);

        Order order = mock(Order.class);

        OrderItem orderItem = new OrderItem(order,menuItem,amount,false);

        assertThat(orderItem.calculatePrice(), is(multipliedPrice));
    }

}