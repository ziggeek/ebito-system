package com.ebito.exceptionhandler.model;

import org.springframework.http.HttpStatus;

/**
 * Перечисление описывающее коды {@code code} и статусы {@code httpStatus} ошибок.
 * {@code code}:
 * 105 неизвестная ошибка;
 * с 1000 до 1999 валидация и некорректные данные переданные в систему;
 * с 2000 до 3999 ошибки в ходе исполнения программы и несвязанные с бизнес-частью;
 * с 4000 до 5999 бизнес-ошибки;
 * c 6000 до 8499 любые взаимодествия со сторонними сервисами.
 * c 8500 до 8999 любые взаимодествия со сторонними сервисами, которые можно пытаться повторить.
 */
public enum ErrorCode {

    UNKNOWN(105, HttpStatus.INTERNAL_SERVER_ERROR),

    BAD_REQUEST(1000, HttpStatus.BAD_REQUEST),
    VALIDATION_ERROR(1001, HttpStatus.BAD_REQUEST),
    INCORRECT_DOCUMENT_TYPE_ERROR(1002, HttpStatus.BAD_REQUEST),

    RUNTIME_EXCEPTION(2000, HttpStatus.INTERNAL_SERVER_ERROR),
    INTERNAL_EXCEPTION(2001, HttpStatus.INTERNAL_SERVER_ERROR),
    ILLEGAL_STATE_EXCEPTION(2002, HttpStatus.INTERNAL_SERVER_ERROR),
    ILLEGAL_ARGUMENT_EXCEPTION(2003, HttpStatus.INTERNAL_SERVER_ERROR),
    NULL_POINTER_EXCEPTION(2004, HttpStatus.INTERNAL_SERVER_ERROR),
    CLASS_CAST_EXCEPTION(2005, HttpStatus.INTERNAL_SERVER_ERROR),
    FILE_PROCESSING_EXCEPTION(2006, HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_URL_EXCEPTION(2007, HttpStatus.INTERNAL_SERVER_ERROR),
    BUCKET_PROCESSING_EXCEPTION(2008, HttpStatus.INTERNAL_SERVER_ERROR),

    BUSINESS_EXCEPTION(4000, HttpStatus.INTERNAL_SERVER_ERROR),
    RESOURCE_NOT_FOUND_EXCEPTION(4001, HttpStatus.NOT_FOUND),
    RESOURCE_CREATION_EXCEPTION(4002, HttpStatus.INTERNAL_SERVER_ERROR),
    RESOURCE_UPDATION_EXCEPTION(4003, HttpStatus.INTERNAL_SERVER_ERROR),
    RESOURCE_DELETION_EXCEPTION(4004, HttpStatus.INTERNAL_SERVER_ERROR),

    REMOTE_EXCEPTION(8000, HttpStatus.BAD_GATEWAY),
    FEIGN_EXCEPTION(8001, HttpStatus.BAD_GATEWAY),


    FEIGN_RETRYABLE_EXCEPTION(8500, HttpStatus.BAD_GATEWAY),
    REMOTE_RETRYABLE_EXCEPTION(8501, HttpStatus.BAD_GATEWAY),
    FEIGN_SERVICE_UNAVAILABLE_EXCEPTION(8507, HttpStatus.BAD_GATEWAY),
    FEIGN_GATEWAY_TIMEOUT_EXCEPTION(8508, HttpStatus.BAD_GATEWAY);

    private final int code;
    private final HttpStatus httpStatus;

    ErrorCode(int code, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
