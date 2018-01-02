package net.lebedko.web.validator.user;

import net.lebedko.entity.user.EmailAddress;
import net.lebedko.web.validator.Errors;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.junit.runners.Parameterized.*;

@RunWith(Enclosed.class)
public class EmailAddressValidatorTest {

    @RunWith(Parameterized.class)
    public static class EmailAddressValidatorTest_malformedEmails {
        @Parameters
        public static Collection<EmailAddress> data() {
            return Arrays.asList(
                    new EmailAddress(" "),
                    new EmailAddress("toolongemailnotvalidone@indx.zz"),
                    new EmailAddress("@jfajsiej.com"),
                    new EmailAddress("asdas@dasd@"),
                    new EmailAddress("///asdas@dasd@"),
                    new EmailAddress("asdas@@das.com")
            );
        }

        private EmailAddressValidator validator = new EmailAddressValidator();
        private Errors errors = new Errors();

        @Parameter
        public EmailAddress emailAddress;

        @Test
        public void validate() {
            validator.validate(emailAddress, errors);

            assertTrue(errors.hasErrors());
        }
    }

    @RunWith(Parameterized.class)
    public static class EmailAddressValidatorTest_regularEmails {
        @Parameters
        public static Collection<EmailAddress> data() {
            return Arrays.asList(
                    new EmailAddress("123post@gmail.com"),
                    new EmailAddress("post@gmail.com.ua"),
                    new EmailAddress("post@gmail123.com.ua"),
                    new EmailAddress("post-valid@gmail123.com.ua"),
                    new EmailAddress("post_valid@gmail123.com.ua"),
                    new EmailAddress("valid.email@gmail123.com.ua")
            );
        }

        private Errors errors = new Errors();
        private EmailAddressValidator validator = new EmailAddressValidator();

        @Parameter
        public EmailAddress emailAddress;

        @Test
        public void validate(){
            validator.validate(emailAddress, errors);

            assertFalse(errors.hasErrors());
        }
    }
}