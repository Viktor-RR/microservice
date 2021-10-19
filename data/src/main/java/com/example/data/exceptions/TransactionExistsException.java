package com.example.data.exceptions;

public class TransactionExistsException extends RuntimeException {
    public TransactionExistsException() {
    }

    public TransactionExistsException(String message) {
        super(message);
    }

    public TransactionExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionExistsException(Throwable cause) {
        super(cause);
    }

    protected TransactionExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
