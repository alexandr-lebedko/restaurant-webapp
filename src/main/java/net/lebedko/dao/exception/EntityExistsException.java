package net.lebedko.dao.exception;

/**
 * alexandr.lebedko : 08.05.2017.
 */

public class EntityExistsException extends DataAccessException {

    public EntityExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityExistsException(Throwable cause) {
        super(cause);
    }

}
