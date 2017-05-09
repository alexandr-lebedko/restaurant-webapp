package net.lebedko.entity.general;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * alexandr.lebedko : 22.03.2017.
 */

public class PriceTest {
    private static final double DELTA = 0.01;

    @Test
    public void negativeArgumentValidityTest() {
        Price price = new Price(-7);

        assertFalse(price.isValid());
    }

    @Test
    public void validArgumentValidityTest() {
        Price price = new Price(123.10);

        assertTrue(price.isValid());
    }

    @Test
    public void nanDoubleValueValidityTest() {
        Price price = new Price(Double.NaN);

        assertThat(price.isValid(), is(false));
    }

    @Test
    public void infinityDoubleValueValidityTest() {
        Price price0 = new Price(Double.NEGATIVE_INFINITY);
        Price price1 = new Price(Double.POSITIVE_INFINITY);

        assertThat(price0.isValid(), is(not(true)));
        assertThat(price1.isValid(), is(not(true)));
    }

    @Test
    public void positiveInfinityDoubleValueValidityTest() {
        new Price(Double.POSITIVE_INFINITY);
    }

    @Test
    public void roundingInitialValueTest() {
        double initialPriceValue = 123.113123214325346;
        double expectedPriceValue = 123.11;

        Price price = new Price(initialPriceValue);
        assertEquals(expectedPriceValue, price.getDoubleValue(), DELTA);
    }

    @Test
    public void sumPriceMethodTest() {
        double priceValue0 = 123.10;
        double priceValue1 = 100.20;
        double priceValueSum = priceValue0 + priceValue1;

        Price price0 = new Price(priceValue0);
        Price price1 = new Price(priceValue1);

        assertEquals(priceValueSum, price0.sum(price1).getDoubleValue(), DELTA);
    }

    @Test
    public void multiplyPriceTest() throws Exception {
        double priceValue = 123.22;
        int multiplier = 3;
        double expected = priceValue * multiplier;

        Price actual = new Price(priceValue).multiplyBy(multiplier);

        assertEquals("Double values should be equal!", expected, actual.getDoubleValue(), DELTA);
    }
}