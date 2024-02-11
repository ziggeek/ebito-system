package com.ebito.exceptionhandler;

import com.ebito.exceptionhandler.common.ApplicationInfo;
import com.ebito.exceptionhandler.common.LocaleUtil;
import com.ebito.exceptionhandler.common.WebUtil;
import com.ebito.exceptionhandler.exception.*;
import com.ebito.exceptionhandler.model.ErrorCode;
import com.ebito.exceptionhandler.model.ErrorResponse;
import com.ebito.exceptionhandler.model.Validation;
import com.fasterxml.jackson.databind.JsonMappingException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.lang.IllegalStateException;
import java.util.List;
import java.util.Locale;

import static com.ebito.exceptionhandler.util.Utils.isInstance;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toUnmodifiableList;


@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

    private static String FEIGN_EXCEPTION_CLASSNAME = "feign.FeignException";

    private static final Logger log = LoggerFactory.getLogger(BaseExceptionHandler.class);

    private FeignExceptionResolver feignExceptionResolver;
    


    @Autowired(required = false)
    public void setFeignExceptionResolver(FeignExceptionResolver feignExceptionResolver) {
        this.feignExceptionResolver = feignExceptionResolver;
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
        if (isInstance(ex, FEIGN_EXCEPTION_CLASSNAME)) {
            return feignExceptionResolver.handleException((FeignException) ex, request);
        } else {
            ErrorResponse<Void> responseBody = ErrorResponse.buildInternalErrorBody(
                    ApplicationInfo.getName(),
                    WebUtil.getEndpoint(request),
                    ErrorCode.RUNTIME_EXCEPTION.getCode());

            log.error("Runtime exception!", ex);
            return handleExceptionInternal(
                    ex,
                    responseBody,
                    new HttpHeaders(),
                    ErrorCode.RUNTIME_EXCEPTION.getHttpStatus(),
                    request);
        }
    }

    @ExceptionHandler(IllegalStateException.class)
    protected ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex, WebRequest request) {
        ErrorResponse<Void> responseBody = ErrorResponse.buildInternalErrorBody(
                ApplicationInfo.getName(),
                WebUtil.getEndpoint(request),
                ErrorCode.ILLEGAL_STATE_EXCEPTION.getCode());

        log.error("Illegal state exception!", ex);
        return handleExceptionInternal(
                ex,
                responseBody,
                new HttpHeaders(),
                ErrorCode.ILLEGAL_STATE_EXCEPTION.getHttpStatus(),
                request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ErrorResponse<Void> responseBody = ErrorResponse.buildInternalErrorBody(
                ApplicationInfo.getName(),
                WebUtil.getEndpoint(request),
                ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getCode());

        log.error("Illegal argument exception!", ex);
        return handleExceptionInternal(
                ex,
                responseBody,
                new HttpHeaders(),
                ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getHttpStatus(),
                request);
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<Object> handleNullPointerException(NullPointerException ex, WebRequest request) {
        ErrorResponse<Void> responseBody = ErrorResponse.buildInternalErrorBody(
                ApplicationInfo.getName(),
                WebUtil.getEndpoint(request),
                ErrorCode.NULL_POINTER_EXCEPTION.getCode());

        log.error("Null pointer exception!", ex);
        return handleExceptionInternal(
                ex,
                responseBody,
                new HttpHeaders(),
                ErrorCode.NULL_POINTER_EXCEPTION.getHttpStatus(),
                request);
    }

    @ExceptionHandler(ClassCastException.class)
    protected ResponseEntity<Object> handleClassCastException(ClassCastException ex, WebRequest request) {
        ErrorResponse<Void> responseBody = ErrorResponse.buildInternalErrorBody(
                ApplicationInfo.getName(),
                WebUtil.getEndpoint(request),
                ErrorCode.CLASS_CAST_EXCEPTION.getCode());

        log.error("Class cast exception!", ex);
        return handleExceptionInternal(
                ex,
                responseBody,
                new HttpHeaders(),
                ErrorCode.CLASS_CAST_EXCEPTION.getHttpStatus(),
                request);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleUnknownException(Exception ex, WebRequest request) {
        ErrorResponse<Void> responseBody = ErrorResponse.buildInternalErrorBody(
                ApplicationInfo.getName(),
                WebUtil.getEndpoint(request),
                ErrorCode.UNKNOWN.getCode());

        log.error("Unknown error!", ex);
        return handleExceptionInternal(
                ex,
                responseBody,
                new HttpHeaders(),
                ErrorCode.UNKNOWN.getHttpStatus(),
                request);
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
        ErrorResponse<Void> responseBody = ErrorResponse.buildInternalErrorBody(
                ApplicationInfo.getName(),
                WebUtil.getEndpoint(request),
                ErrorCode.BUSINESS_EXCEPTION.getCode());

        log.error("Business exception!", ex);
        return handleExceptionInternal(
                ex,
                responseBody,
                new HttpHeaders(),
                ErrorCode.BUSINESS_EXCEPTION.getHttpStatus(),
                request);
    }

    @ExceptionHandler(ResourceCreationException.class)
    protected ResponseEntity<Object> handleResourceCreationException(ResourceCreationException ex, WebRequest request) {
        ErrorResponse<Void> responseBody = ErrorResponse.buildInternalErrorBody(
                ApplicationInfo.getName(),
                WebUtil.getEndpoint(request),
                ErrorCode.RESOURCE_CREATION_EXCEPTION.getCode());

        log.error("Resource creation exception!", ex);
        return handleExceptionInternal(
                ex,
                responseBody,
                new HttpHeaders(),
                ErrorCode.RESOURCE_CREATION_EXCEPTION.getHttpStatus(),
                request);
    }

    @ExceptionHandler(ResourceDeleteException.class)
    protected ResponseEntity<Object> handleResourceDeletionException(ResourceDeleteException ex, WebRequest request) {
        ErrorResponse<Void> responseBody = ErrorResponse.buildInternalErrorBody(
                ApplicationInfo.getName(),
                WebUtil.getEndpoint(request),
                ErrorCode.RESOURCE_DELETION_EXCEPTION.getCode());

        log.error("Resource deletion exception!", ex);
        return handleExceptionInternal(
                ex,
                responseBody,
                new HttpHeaders(),
                ErrorCode.RESOURCE_DELETION_EXCEPTION.getHttpStatus(),
                request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorResponse<Void> responseBody = ErrorResponse.<Void>builder()
                .application(ApplicationInfo.getName())
                .errorCode(ErrorCode.RESOURCE_NOT_FOUND_EXCEPTION.getCode())
                .message(Locale.ENGLISH, "Resource not found")
                .message(LocaleUtil.RUSSIAN, "Ресурс не найден")
                .endpoint(WebUtil.getEndpoint(request))
                .build();

        log.error("Resource not found exception!", ex);
        return handleExceptionInternal(
                ex,
                responseBody,
                new HttpHeaders(),
                ErrorCode.RESOURCE_NOT_FOUND_EXCEPTION.getHttpStatus(),
                request);
    }

    @ExceptionHandler(ResourceUpdateException.class)
    protected ResponseEntity<Object> handleResourceUpdationException(ResourceUpdateException ex, WebRequest request) {
        ErrorResponse<Void> responseBody = ErrorResponse.buildInternalErrorBody(
                ApplicationInfo.getName(),
                WebUtil.getEndpoint(request),
                ErrorCode.RESOURCE_UPDATION_EXCEPTION.getCode());

        log.error("Resource updation exception!", ex);
        return handleExceptionInternal(
                ex,
                responseBody,
                new HttpHeaders(),
                ErrorCode.RESOURCE_UPDATION_EXCEPTION.getHttpStatus(),
                request);
    }

    @ExceptionHandler(InternalException.class)
    protected ResponseEntity<Object> handleInternalException(InternalException ex, WebRequest request) {
        ErrorResponse<Void> responseBody = ErrorResponse.buildInternalErrorBody(
                ApplicationInfo.getName(),
                WebUtil.getEndpoint(request),
                ErrorCode.INTERNAL_EXCEPTION.getCode());

        log.error("Internal exception!", ex);
        return handleExceptionInternal(
                ex,
                responseBody,
                new HttpHeaders(),
                ErrorCode.INTERNAL_EXCEPTION.getHttpStatus(),
                request);
    }

    @ExceptionHandler(RemoteException.class)
    protected ResponseEntity<Object> handleRemoteException(RemoteException ex, WebRequest request) {
        if (isInstance(ex.getCause(), FEIGN_EXCEPTION_CLASSNAME)) {
            return feignExceptionResolver.handleException((FeignException) ex.getCause(), request);
        } else {
            ErrorResponse<Void> responseBody = ErrorResponse.<Void>builder()
                    .application(ApplicationInfo.getName())
                    .errorCode(ErrorCode.REMOTE_EXCEPTION.getCode())
                    .message(Locale.ENGLISH, "Error when try to call another service")
                    .message(LocaleUtil.RUSSIAN, "Ошибка при попытке вызова стороннего сервиса")
                    .endpoint(WebUtil.getEndpoint(request))
                    .build();

            log.error("Remote exception!", ex);
            return handleExceptionInternal(
                    ex,
                    responseBody,
                    new HttpHeaders(),
                    ErrorCode.REMOTE_EXCEPTION.getHttpStatus(),
                    request);
        }
    }

    @ExceptionHandler(RemoteRetryableException.class)
    protected ResponseEntity<Object> handleRemoteException(RemoteRetryableException ex, WebRequest request) {
        ErrorResponse<Void> responseBody = ErrorResponse.<Void>builder()
                .application(ApplicationInfo.getName())
                .errorCode(ErrorCode.REMOTE_RETRYABLE_EXCEPTION.getCode())
                .message(Locale.ENGLISH, "Error when try to call another service")
                .message(LocaleUtil.RUSSIAN, "Ошибка при попытке вызова стороннего сервиса")
                .endpoint(WebUtil.getEndpoint(request))
                .build();

        log.error("Remote Retryable exception!", ex);
        return handleExceptionInternal(
                ex,
                responseBody,
                new HttpHeaders(),
                ErrorCode.REMOTE_EXCEPTION.getHttpStatus(),
                request);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        final List<Validation> validations = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .map(this::rawMessageToValidation)
                .collect(toUnmodifiableList());

        final ErrorResponse<List<Validation>> response = ErrorResponse.<List<Validation>>builder()
                .application(ApplicationInfo.getName())
                .errorCode(ErrorCode.BAD_REQUEST.getCode())
                .message(Locale.ENGLISH, "Message not valid")
                .message(LocaleUtil.RUSSIAN, "Переданное сообщение невалидно")
                .endpoint(WebUtil.getEndpoint(request))
                .payload(validations)
                .build();

        log.trace("Bad Request", ex);
        return handleExceptionInternal(ex, response, new HttpHeaders(), ErrorCode.BAD_REQUEST.getHttpStatus(), request);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        final Validation validation = new Validation(ex.getName(), "Передан неверный тип данных");

        final ErrorResponse<Validation> response = ErrorResponse.<Validation>builder()
                .application(ApplicationInfo.getName())
                .errorCode(ErrorCode.BAD_REQUEST.getCode())
                .message(Locale.ENGLISH, "Message not valid")
                .message(LocaleUtil.RUSSIAN, "Переданное сообщение невалидно")
                .endpoint(WebUtil.getEndpoint(request))
                .payload(validation)
                .build();

        log.trace("Bad Request", ex);
        return handleExceptionInternal(ex, response, new HttpHeaders(), ErrorCode.BAD_REQUEST.getHttpStatus(), request);
    }

    @ExceptionHandler(value = IncorrectDocumentTypeException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatchException(IncorrectDocumentTypeException ex, WebRequest request) {
        final Validation validation = new Validation("documentCode", "Передан неверный тип");

        final ErrorResponse<Validation> response = ErrorResponse.<Validation>builder()
                .application(ApplicationInfo.getName())
                .errorCode(ErrorCode.INCORRECT_DOCUMENT_TYPE_ERROR.getCode())
                .message(Locale.ENGLISH, "Message not valid")
                .message(LocaleUtil.RUSSIAN, "Переданное сообщение невалидно")
                .endpoint(WebUtil.getEndpoint(request))
                .payload(validation)
                .build();

        log.trace("Bad Request", ex);
        return handleExceptionInternal(ex, response, new HttpHeaders(), ErrorCode.INCORRECT_DOCUMENT_TYPE_ERROR.getHttpStatus(), request);
    }

    @ExceptionHandler(FileProcessingException.class)
    public ResponseEntity<Object> handleFileProcessingException(FileProcessingException ex, WebRequest request) {
        ErrorResponse<Void> responseBody = ErrorResponse.buildInternalErrorBody(
                ApplicationInfo.getName(),
                WebUtil.getEndpoint(request),
                ErrorCode.FILE_PROCESSING_EXCEPTION.getCode());

        log.error("File processing error!", ex);
        return handleExceptionInternal(
                ex,
                responseBody,
                new HttpHeaders(),
                ErrorCode.FILE_PROCESSING_EXCEPTION.getHttpStatus(),
                request);
    }

    @ExceptionHandler(InvalidUrlException.class)
    public ResponseEntity<Object> handleInvalidUrlException(InvalidUrlException ex, WebRequest request) {
        ErrorResponse<Void> responseBody = ErrorResponse.buildInternalErrorBody(
                ApplicationInfo.getName(),
                WebUtil.getEndpoint(request),
                ErrorCode.INVALID_URL_EXCEPTION.getCode());

        log.error("File url error!", ex);
        return handleExceptionInternal(
                ex,
                responseBody,
                new HttpHeaders(),
                ErrorCode.INVALID_URL_EXCEPTION.getHttpStatus(),
                request);
    }

    @ExceptionHandler(BucketProcessingException.class)
    public ResponseEntity<Object> handleResourceAccessException(BucketProcessingException ex, WebRequest request) {
        ErrorResponse<Void> responseBody = ErrorResponse.buildInternalErrorBody(
                ApplicationInfo.getName(),
                WebUtil.getEndpoint(request),
                ErrorCode.BUCKET_PROCESSING_EXCEPTION.getCode());

        log.error("Bucket processing error!", ex);
        return handleExceptionInternal(
                ex,
                responseBody,
                new HttpHeaders(),
                ErrorCode.BUCKET_PROCESSING_EXCEPTION.getHttpStatus(),
                request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex,
                                                               HttpHeaders headers,
                                                               HttpStatus status,
                                                               WebRequest request) {
        final ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;
        final Validation validation = new Validation(ex.getVariableName(), "Параметр пути должен быть задан");

        final ErrorResponse<List<Validation>> response = ErrorResponse.<List<Validation>>builder()
                .application(ApplicationInfo.getName())
                .errorCode(ErrorCode.BAD_REQUEST.getCode())
                .message(Locale.ENGLISH, "Message not valid")
                .message(LocaleUtil.RUSSIAN, "Переданное сообщение невалидно")
                .endpoint(WebUtil.getEndpoint(request))
                .payload(List.of(validation))
                .build();

        return handleExceptionInternal(ex, response, new HttpHeaders(), errorCode.getHttpStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {
        final ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;
        final Validation validation = new Validation(ex.getParameterName(), ex.getMessage());

        ErrorResponse<Validation> response = ErrorResponse.<Validation>builder()
                .application(ApplicationInfo.getName())
                .errorCode(errorCode.getCode())
                .message(Locale.ENGLISH, "Message not valid")
                .message(LocaleUtil.RUSSIAN, "Переданное сообщение невалидно")
                .endpoint(WebUtil.getEndpoint(request))
                .payload(validation)
                .build();

        return handleExceptionInternal(ex, response, new HttpHeaders(), errorCode.getHttpStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ErrorResponse<Validation> responseBody = ErrorResponse.<Validation>builder()
                .application(ApplicationInfo.getName())
                .errorCode(ErrorCode.BAD_REQUEST.getCode())
                .message(Locale.ENGLISH, "Message not readable")
                .message(LocaleUtil.RUSSIAN, "Невозможно прочитать переданное сообщение")
                .endpoint(WebUtil.getEndpoint(request))
                .payload(buildIncorrectJacksonMappingPayload(ex))
                .build();

        log.trace("Bad Request!", ex);
        return handleExceptionInternal(
                ex,
                responseBody,
                new HttpHeaders(),
                ErrorCode.BAD_REQUEST.getHttpStatus(),
                request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ErrorResponse<List<Validation>> responseBody = ErrorResponse.<List<Validation>>builder()
                .application(ApplicationInfo.getName())
                .errorCode(ErrorCode.VALIDATION_ERROR.getCode())
                .message(Locale.ENGLISH, "Message not valid")
                .message(LocaleUtil.RUSSIAN, "Переданное сообщение невалидно")
                .endpoint(WebUtil.getEndpoint(request))
                .payload(buildNotValidPayload(ex.getBindingResult()))
                .build();

        log.trace("Validation error!", ex);
        return handleExceptionInternal(
                ex,
                responseBody,
                new HttpHeaders(),
                ErrorCode.VALIDATION_ERROR.getHttpStatus(),
                request);
    }

    private Validation buildIncorrectJacksonMappingPayload(HttpMessageNotReadableException ex) {
        try {
            JsonMappingException jme = (JsonMappingException) ex.getCause();

            return ofNullable(jme)
                    .map(jacksonException -> {
                        if (jacksonException.getPath() != null && !jacksonException.getPath().isEmpty()) {
                            String fieldName = jacksonException.getPath()
                                    .get(0)
                                    .getFieldName();
                            return new Validation(fieldName, jacksonException.getMessage());
                        } else {
                            return new Validation("unknown", jacksonException.getMessage());
                        }
                    })
                    .orElse(new Validation(null, ex.getMessage()));
        } catch (ClassCastException classCastException) {
            log.warn("Error when try to get information about validation", classCastException);
            return null;
        }
    }

    private List<Validation> buildNotValidPayload(BindingResult bindingResult) {
        return bindingResult
                .getFieldErrors()
                .stream()
                .map(fieldError -> new Validation(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(toUnmodifiableList());
    }

    /**
     * Transforms plain text to json message;
     * Example:
     * firstName Поле является обязательным -> {"field":"firstName","message":"Поле является обязательным"}
     *
     * @param rawMessage message as plain text
     * @return Validation message
     */
    private Validation rawMessageToValidation(String rawMessage) {
        String[] fieldAndMessage = rawMessage.split(" ", 2);
        String field = fieldAndMessage[0];
        String message = fieldAndMessage[1];

        return new Validation(field, message);
    }
}