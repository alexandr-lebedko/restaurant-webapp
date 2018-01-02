package net.lebedko.web.validator.item;

import net.lebedko.entity.general.StringI18N;
import net.lebedko.entity.item.Category;
import net.lebedko.web.validator.Errors;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import static org.junit.Assert.*;
import static org.junit.runners.Parameterized.*;

public class CategoryValidatorTest {
    private Errors errors = new Errors();
    private CategoryValidator validator = new CategoryValidator();


    @Test
    public void validate_nameLessThan2Symbols() throws Exception {
        Category category = new Category(StringI18N.builder()
                .add(Locale.ENGLISH, "A")
                .build());

        validator.validate(category, errors);

        assertTrue(errors.hasErrors());
    }

    @Test
    public void validate_nameBiggerThan25Symbols() {
        Category category = new Category(StringI18N.builder()
                .add(Locale.ENGLISH, "Very Huge Category Title is Not Valid")
                .build());

        validator.validate(category, errors);

        assertTrue(errors.hasErrors());
    }

    @RunWith(Parameterized.class)
    public static class CategoryValidatorTest_regularCategories {

        @Parameters
        public static Collection<Category> data() {
            return Arrays.asList(
                    new Category(StringI18N.builder()
                            .add(Locale.ENGLISH, "Soups")
                            .build()),
                    new Category(StringI18N.builder()
                            .add(Locale.ENGLISH, "Drinks")
                            .build()),
                    new Category(StringI18N.builder()
                            .add(Locale.ENGLISH, "Meat")
                            .build()));
        }

        private Errors errors = new Errors();
        private CategoryValidator validator = new CategoryValidator();

        @Parameter
        public Category category;

        @Test
        public void validate() {
            validator.validate(category, errors);

            assertFalse(errors.hasErrors());
        }
    }
}