package net.lebedko.dao.exception;

/**
 * alexandr.lebedko : 11.05.2017.
 */
public class TransactionException extends DataAccessException {
    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionException(Throwable cause) {
        super(cause);
    }
}
