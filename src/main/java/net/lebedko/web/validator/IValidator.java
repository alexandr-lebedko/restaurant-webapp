package net.lebedko.web.validator;

/**
 * alexandr.lebedko : 15.06.2017
 */

import net.lebedko.entity.Validatable;

import java.util.Map;

public interface IValidator<T extends Validatable> {
    void validate(T t, Errors errors);
}
