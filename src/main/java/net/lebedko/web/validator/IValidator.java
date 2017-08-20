package net.lebedko.web.validator;

/**
 * alexandr.lebedko : 15.06.2017
 */

public interface IValidator<T> {
    void validate(T t, Errors errors);
}
