package net.lebedko.web.validator;

public interface Validator<T> {
    void validate(T t, Errors errors);
}
