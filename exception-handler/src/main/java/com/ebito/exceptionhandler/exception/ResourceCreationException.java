package com.ebito.exceptionhandler.exception;

public class ResourceCreationException extends BusinessException {

    public ResourceCreationException(String message) {
        super(message);
    }

    public ResourceCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceCreationException(Throwable cause) {
        super(cause);
    }
}
