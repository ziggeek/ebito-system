package com.ebito.exceptionhandler.exception;

public class RemoteRetryableException extends RemoteException {

    public RemoteRetryableException(String message) {
        super(message);
    }

    public RemoteRetryableException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoteRetryableException(Throwable cause) {
        super(cause);
    }
}
