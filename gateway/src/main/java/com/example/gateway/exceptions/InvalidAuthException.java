package com.example.gateway.exceptions;

public class InvalidAuthException extends RuntimeException {
    public InvalidAuthException(String message) {
    }

    public InvalidAuthException() {
    }

    public InvalidAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAuthException(Throwable cause) {
        super(cause);
    }

    protected InvalidAuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
