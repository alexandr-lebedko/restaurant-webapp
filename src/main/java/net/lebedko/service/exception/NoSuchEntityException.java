package net.lebedko.service.exception;

/**
 * alexandr.lebedko : 11.05.2017.
 */
public class NoSuchEntityException extends ServiceException {
    public NoSuchEntityException() {
    }

    public NoSuchEntityException(String message) {
        super(message);
    }
}
