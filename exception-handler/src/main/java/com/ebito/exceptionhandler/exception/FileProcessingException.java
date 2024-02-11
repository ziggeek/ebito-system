package com.ebito.exceptionhandler.exception;

/**
 * Исключение , которое возникает при обработке файла.
 *
 */
public class FileProcessingException extends AbstractException {
    public FileProcessingException(String message) {
        super(message);
    }


}