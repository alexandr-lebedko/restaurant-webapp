package net.lebedko.dao.exception;

public class DataAccessException extends RuntimeException{
    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(Throwable cause) {
        super(cause);
    }
}
