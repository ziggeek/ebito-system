package com.ebito.exceptionhandler.exception;

public class ResourceDeleteException extends BusinessException {

    public ResourceDeleteException(String message) {
        super(message);
    }

    public ResourceDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceDeleteException(Throwable cause) {
        super(cause);
    }
}
