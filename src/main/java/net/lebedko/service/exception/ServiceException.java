package net.lebedko.service.exception;

public class ServiceException extends RuntimeException {
    public ServiceException() {
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message) {
        super(message);
    }
}
