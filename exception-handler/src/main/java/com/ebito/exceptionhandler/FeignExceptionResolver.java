package com.ebito.exceptionhandler;

import com.ebito.exceptionhandler.common.ApplicationInfo;
import com.ebito.exceptionhandler.common.LocaleUtil;
import com.ebito.exceptionhandler.common.WebUtil;
import com.ebito.exceptionhandler.model.ErrorCode;
import com.ebito.exceptionhandler.model.ErrorResponse;
import feign.FeignException;
import feign.RetryableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import java.util.Locale;

import static com.ebito.exceptionhandler.util.Utils.isInstance;


@Component
@ConditionalOnClass(FeignException.class)
public class FeignExceptionResolver {

    private static String GATEWAY_TIMEOUT_CLASSNAME = "feign.GatewayTimeout";

    private static String SERVICE_UNAVAILABLE_CLASSNAME = "feign.ServiceUnavailable";

    private static final Logger log = LoggerFactory.getLogger(FeignExceptionResolver.class);

    public ResponseEntity<Object> handleException(FeignException ex, WebRequest request) {
        if (ex instanceof RetryableException) {
            ErrorResponse<Void> responseBody = ErrorResponse.<Void>builder()
                    .application(ApplicationInfo.getName())
                    .errorCode(ErrorCode.FEIGN_RETRYABLE_EXCEPTION.getCode())
                    .message(Locale.ENGLISH, "Error when try to call another service")
                    .message(LocaleUtil.RUSSIAN, "Ошибка при попытке вызова стороннего сервиса")
                    .endpoint(WebUtil.getEndpoint(request))
                    .build();

            log.error("Feign Retryable exception!", ex);
            return handleExceptionInternal(
                    ex,
                    responseBody,
                    new HttpHeaders(),
                    ErrorCode.FEIGN_RETRYABLE_EXCEPTION.getHttpStatus(),
                    request);
        } else if (isInstance(ex, GATEWAY_TIMEOUT_CLASSNAME)){
            ErrorResponse<Void> responseBody = ErrorResponse.<Void>builder()
                    .application(ApplicationInfo.getName())
                    .errorCode(ErrorCode.FEIGN_GATEWAY_TIMEOUT_EXCEPTION.getCode())
                    .message(Locale.ENGLISH, "Error when try to call another service")
                    .message(LocaleUtil.RUSSIAN, "Ошибка при попытке вызова стороннего сервиса")
                    .endpoint(WebUtil.getEndpoint(request))
                    .build();

            log.error("Feign GatewayTimeout exception!", ex);
            return handleExceptionInternal(
                    ex,
                    responseBody,
                    new HttpHeaders(),
                    ErrorCode.FEIGN_GATEWAY_TIMEOUT_EXCEPTION.getHttpStatus(),
                    request);
        } else if (isInstance(ex, SERVICE_UNAVAILABLE_CLASSNAME)) {
            ErrorResponse<Void> responseBody = ErrorResponse.<Void>builder()
                    .application(ApplicationInfo.getName())
                    .errorCode(ErrorCode.FEIGN_SERVICE_UNAVAILABLE_EXCEPTION.getCode())
                    .message(Locale.ENGLISH, "Error when try to call another service")
                    .message(LocaleUtil.RUSSIAN, "Ошибка при попытке вызова стороннего сервиса")
                    .endpoint(WebUtil.getEndpoint(request))
                    .build();

            log.error("Feign ServiceUnavailable exception!", ex);
            return handleExceptionInternal(
                    ex,
                    responseBody,
                    new HttpHeaders(),
                    ErrorCode.FEIGN_SERVICE_UNAVAILABLE_EXCEPTION.getHttpStatus(),
                    request);
        } else {
            ErrorResponse<String> responseBody = ErrorResponse.<String>builder()
                    .application(ApplicationInfo.getName())
                    .errorCode(ErrorCode.FEIGN_EXCEPTION.getCode())
                    .message(Locale.ENGLISH, "Error when try to call another service")
                    .message(LocaleUtil.RUSSIAN, "Ошибка при попытке вызова стороннего сервиса")
                    .endpoint(WebUtil.getEndpoint(request))
                    .payload(ex.contentUTF8())
                    .build();

            log.error("Feign exception!", ex);
            return handleExceptionInternal(
                    ex,
                    responseBody,
                    new HttpHeaders(),
                    ErrorCode.FEIGN_EXCEPTION.getHttpStatus(),
                    request);
        }
    }

    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(body, headers, status);
    }
}
