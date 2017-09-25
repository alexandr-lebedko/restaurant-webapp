package net.lebedko.util;

/**
 * alexandr.lebedko : 20.09.2017.
 */
@FunctionalInterface
public interface CheckedFunction<T, R, E extends Exception> {
    R apply(T t) throws E;
}
