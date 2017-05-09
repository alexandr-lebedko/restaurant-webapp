package net.lebedko.entity.order;

import net.lebedko.entity.general.Price;
import net.lebedko.entity.user.User;
import org.junit.BeforeClass;
import org.junit.Test;

import static net.lebedko.entity.order.Order.OrderStatus.ACTIVE;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * alexandr.lebedko : 23.03.2017.
 */
public class OrderTest {
    private static User validClient;

    @BeforeClass
    public static void startUp() {
        validClient = mock(User.class);
        when(validClient.isValid()).thenReturn(true);
    }

    @Test(expected = NullPointerException.class)
    public void nullArgumentTest() {
        new Order(0, null, null);
    }

    @Test
    public void invalidArgumentsValidityTest() {
        User client = mock(User.class);
        when(client.isValid()).thenReturn(false);

        Order order = new Order(1, client, ACTIVE);

        assertThat("Order should be invalid!", order.isValid(), is(false));
    }

    @Test
    public void validArgumentsValidityTest() {
        Order order = new Order(1, validClient, ACTIVE);

        assertThat("Order should be valid!", order.isValid(), is(true));
    }

    @Test
    public void countSumTest() {
        Price price1 = new Price(123.10);
        Price price2 = new Price(100.10);

        Price sum = price1.sum(price2);

        OrderItem orderItemOne = mock(OrderItem.class);
        OrderItem orderItemTwo = mock(OrderItem.class);

        when(orderItemOne.calculatePrice()).thenReturn(price1);
        when(orderItemTwo.calculatePrice()).thenReturn(price2);

        when(orderItemOne.isValid()).thenReturn(true);
        when(orderItemTwo.isValid()).thenReturn(true);

        Order order = new Order(validClient, ACTIVE);

        order.addOrderItem(orderItemOne);
        order.addOrderItem(orderItemTwo);

        assertThat("Counted value should be equal to sum!", order.countSum(), is(sum));
    }

    @Test
    public void addValidOrderItemTest() {
        OrderItem orderItem = mock(OrderItem.class);
        when(orderItem.isValid()).thenReturn(true);

        Order order = new Order(1, validClient, ACTIVE);

        order.addOrderItem(orderItem);

        int size = order.getOrderItems().size();
        assertThat("Size of list should be 1", size, is(1));
    }

    @Test
    public void addInvalidOrderItemTest() {
        OrderItem orderItem = mock(OrderItem.class);
        when(orderItem.isValid()).thenReturn(false);

        Order order = new Order(1, validClient, ACTIVE);

        order.addOrderItem(orderItem);

        int size = order.getOrderItems().size();
        assertThat("Size of list should be 0", size, is(not(1)));
    }
}