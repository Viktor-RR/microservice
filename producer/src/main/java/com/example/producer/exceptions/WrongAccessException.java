package com.example.producer.exceptions;

public class WrongAccessException extends RuntimeException {
    public WrongAccessException(String message) {
    }

    public WrongAccessException() {
    }

    public WrongAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongAccessException(Throwable cause) {
        super(cause);
    }

    protected WrongAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
