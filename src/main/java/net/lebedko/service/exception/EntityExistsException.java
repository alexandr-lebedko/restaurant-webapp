package net.lebedko.service.exception;

/**
 * alexandr.lebedko : 11.05.2017.
 */
public class EntityExistsException extends ServiceException {
    public EntityExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityExistsException(Throwable cause) {
        super(cause);
    }
}
