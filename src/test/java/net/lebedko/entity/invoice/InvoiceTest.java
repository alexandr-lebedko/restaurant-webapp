package net.lebedko.entity.invoice;

import net.lebedko.entity.general.Price;
import net.lebedko.entity.order.Order;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * alexandr.lebedko : 21.04.2017.
 */
public class InvoiceTest {

    private static Order validOrder;

    @BeforeClass
    public static void setUp() {
        validOrder = mock(Order.class);
        when(validOrder.isValid()).thenReturn(true);
        when(validOrder.countSum()).thenReturn(new Price(123.10));
    }

    @Test(expected = NullPointerException.class)
    public void nullArgumentsTest() {
        new Invoice(0, null, false, null);
    }

    @Test
    public void creationTimeAutomaticallyCreatedTest() {
        Order order = mock(Order.class);

        Invoice invoice = new Invoice(order, false);

        LocalDateTime creationDateTime = invoice.getCreationDateTime();

        assertNotNull("Creation time should not be null!", creationDateTime);
        assertTrue("Creation time should be before or equal to current time!",
                creationDateTime.isBefore(LocalDateTime.now()) || creationDateTime.isEqual(LocalDateTime.now()));
    }

    @Test
    public void invalidOrderArgumentValidityTest() {
        Order order = mock(Order.class);
        when(order.isValid()).thenReturn(false);

        Invoice invoice = new Invoice(order, true);
        assertFalse(invoice.isValid());
    }

    @Test
    public void futureCreationTimeArgumentValidityTest() {

        LocalDateTime creationTime = LocalDateTime.now().plusDays(1);

        Invoice invoice = new Invoice(1, validOrder, true, creationTime);

        assertFalse("Invoice should be invalid, due to future date!", invoice.isValid());
    }

    @Test
    public void validArgumentsValidityTest() {
        Invoice invoice = new Invoice(1, validOrder, false, LocalDateTime.now());
        assertTrue("Invoice should be valid", invoice.isValid());
    }

    @Test
    public void invalidOrderPriceValidityTest() {
        Order order = mock(Order.class);
        when(order.isValid()).thenReturn(true);
        when(order.countSum()).thenReturn(new Price(-1));

        Invoice invoice = new Invoice(order, false);
        assertFalse("Invoice should be invalid, due to negative price", invoice.isValid());

    }

}