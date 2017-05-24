package net.lebedko.service.exception;

/**
 * alexandr.lebedko : 11.05.2017.
 */
public class UnavailableServiceException extends ServiceException {
    public UnavailableServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnavailableServiceException(Throwable cause) {
        super(cause);
    }
}
