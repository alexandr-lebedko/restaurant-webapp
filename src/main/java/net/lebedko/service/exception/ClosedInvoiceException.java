package net.lebedko.service.exception;

public class ClosedInvoiceException extends ServiceException {
    public ClosedInvoiceException() {
    }

    public ClosedInvoiceException(Throwable cause) {
        super(cause);
    }

    public ClosedInvoiceException(String message) {
        super(message);
    }

}
