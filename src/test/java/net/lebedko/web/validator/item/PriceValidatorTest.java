package net.lebedko.web.validator.item;

import net.lebedko.entity.general.Price;
import net.lebedko.web.validator.Errors;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PriceValidatorTest {
    private PriceValidator validator;
    private Errors errors;

    @Before
    public void setUp() {
        validator = new PriceValidator();
        errors = new Errors();
    }

    @Test
    public void validate_greaterThanZeroPrice() throws Exception {
        Price price = new Price(1d);
        validator.validate(price, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validate_zeroPrice() {
        Price price = new Price(0d);
        validator.validate(price, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validate_nullPrice() {
        Price price = new Price(null);
        validator.validate(price, errors);

        assertTrue(errors.hasErrors());
    }

    @Test
    public void validate_negativePrice() {
        Price price = new Price(-1d);
        validator.validate(price, errors);

        assertTrue(errors.hasErrors());
    }

}