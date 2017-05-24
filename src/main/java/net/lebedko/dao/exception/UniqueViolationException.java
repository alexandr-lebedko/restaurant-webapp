package net.lebedko.dao.exception;

/**
 * alexandr.lebedko : 08.05.2017.
 */

public class UniqueViolationException extends DataAccessException {

    public UniqueViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UniqueViolationException(Throwable cause) {
        super(cause);
    }

}
