package net.lebedko.service.exception;

/**
 * alexandr.lebedko : 11.05.2017.
 */
public class NoSuchEntityException extends ServiceException {
    public NoSuchEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchEntityException(Throwable cause) {
        super(cause);
    }

    public NoSuchEntityException(String message) {
        super(message);
    }
}
