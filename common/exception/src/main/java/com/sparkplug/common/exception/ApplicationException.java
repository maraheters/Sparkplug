package com.sparkplug.common.exception;

public abstract class ApplicationException extends RuntimeException {

    public ApplicationException(String message) {
        super(message);
    }

    public abstract int getStatusCode();
}
