package net.lebedko.service.exception;

public class UnprocessedOrdersException extends ServiceException {
    public UnprocessedOrdersException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnprocessedOrdersException(Throwable cause) {
        super(cause);
    }

    public UnprocessedOrdersException() {
        this("Invoice cannot be closed. It has unprocessed orders");
    }

    public UnprocessedOrdersException(String message) {
        super(message);
    }
}
