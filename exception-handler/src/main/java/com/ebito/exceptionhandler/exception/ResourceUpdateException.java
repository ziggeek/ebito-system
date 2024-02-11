package com.ebito.exceptionhandler.exception;

public class ResourceUpdateException extends BusinessException {

    public ResourceUpdateException(String message) {
        super(message);
    }

    public ResourceUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceUpdateException(Throwable cause) {
        super(cause);
    }
}
