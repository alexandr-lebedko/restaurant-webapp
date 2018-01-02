package net.lebedko.web.validator.user;

import net.lebedko.entity.user.Password;
import net.lebedko.web.validator.Errors;
import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordValidatorTest {
    private Errors errors = new Errors();
    private PasswordValidator validator = new PasswordValidator();

    @Test
    public void validate_passwordLessThanEightSymbols() throws Exception {
        validator.validate(Password.createPasswordFromString("1234567"), errors);

        assertTrue(errors.hasErrors());
    }

    @Test
    public void validate_emptyPassword() {
        validator.validate(Password.createPasswordFromString("   "), errors);

        assertTrue(errors.hasErrors());
    }

    @Test
    public void validate_eightOrMoreSymbolsPassword() {
        validator.validate(Password.createPasswordFromString("12345678"), errors);

        assertFalse(errors.hasErrors());
    }

}