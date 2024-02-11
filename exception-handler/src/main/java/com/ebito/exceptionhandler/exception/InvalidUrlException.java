package com.ebito.exceptionhandler.exception;

/**
 * Исключение, возникающее при невалидном URL.
 *
 */
public class InvalidUrlException extends AbstractException {
    public InvalidUrlException(String message) {
        super(message);
    }
}
