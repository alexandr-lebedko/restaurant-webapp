package net.lebedko.web.validator.user;

import net.lebedko.entity.user.FirstName;
import net.lebedko.web.validator.Errors;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class FirstNameValidatorTest {
    private Errors errors = new Errors();
    private FirstNameValidator validator = new FirstNameValidator();

    @Test
    public void validate_nameLongerThan20Symbols() throws Exception {
        validator.validate(new FirstName("Too Long Name Is Not Valid"), errors);

        assertTrue(errors.hasErrors());
    }

    @Test
    public void validate_emptyName() {
        validator.validate(new FirstName(""), errors);

        assertTrue(errors.hasErrors());
    }

    @Test
    public void validate_containsNumbers() {
        validator.validate(new FirstName("FirstName 123"), errors);

        assertTrue(errors.hasErrors());
    }

    @RunWith(Parameterized.class)
    public static class FirstNameValidatorTest_regularNames {

        @Parameters
        public static Collection<FirstName> data() {
            return Arrays.asList(
                    new FirstName("Alexandr"),
                    new FirstName("D'Addario"),
                    new FirstName("d'Addario"),
                    new FirstName("John-Doe")
            );
        }

        @Parameter
        public FirstName name;

        private Errors errors = new Errors();
        private FirstNameValidator validator = new FirstNameValidator();

        @Test
        public void validate() {
            validator.validate(name, errors);

            assertFalse(errors.hasErrors());
        }

    }

}