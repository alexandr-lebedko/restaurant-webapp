package net.lebedko.dao.exception;

public class UniqueViolationException extends DataAccessException {

    public UniqueViolationException(Throwable cause) {
        super(cause);
    }

}
