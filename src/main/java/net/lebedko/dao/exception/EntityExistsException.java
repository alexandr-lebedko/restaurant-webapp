package net.lebedko.dao.exception;

public class EntityExistsException extends DataAccessException {

    public EntityExistsException(Throwable cause) {
        super(cause);
    }

}
