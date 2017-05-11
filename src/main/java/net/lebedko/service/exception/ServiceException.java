package net.lebedko.service.exception;

/**
 * alexandr.lebedko : 11.05.2017.
 */
public class ServiceException extends Exception {
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
