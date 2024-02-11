package com.ebito.exceptionhandler.model;

import com.ebito.exceptionhandler.common.LocaleUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.lang.Nullable;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ErrorResponse<T> {

    /**
     * Наименование сервиса в котором произошла исключительная ситуация
     */
    private final String application;

    /**
     * Эндпоинт в котором произошла исключительная ситуация
     */
    private final String endpoint;

    /**
     * Код ошибки {@link ErrorCode#getCode()}
     */
    private final int errorCode;

    /**
     * Описание ошибки:
     * ключ - локаль сообщения
     * значение - локализированное описание причины возникновения
     */
    private final Map<String, String> messages;

    /**
     * Опционально: дополнительная информация описывающая ошибку
     */
    @Nullable
    @JsonInclude(Include.NON_NULL)
    private final T payload;

    public ErrorResponse(String application, String endpoint, int errorCode, Map<String, String> messages, @Nullable T payload) {
        this.application = application;
        this.endpoint = endpoint;
        this.errorCode = errorCode;
        this.messages = messages;
        this.payload = payload;
    }

    public static ErrorResponse<Void> empty() {
        return new Builder<Void>()
                .build();
    }

    public static ErrorResponse<Void> buildInternalErrorBody(String application, String endpoint, int errorCode) {
        return ErrorResponse.<Void>builder()
                .application(application)
                .endpoint(endpoint)
                .errorCode(errorCode)
                .message(Locale.ENGLISH, "Internal server error")
                .message(LocaleUtil.RUSSIAN, "Внутренняя ошибка сервера")
                .build();
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static final class Builder<T> {

        private String application;
        private String endpoint;
        private int errorCode;
        private Map<String, String> message;
        private T payload;

        public Builder<T> application(String application) {
            this.application = application;

            return this;
        }

        public Builder<T> endpoint(String endpoint) {
            this.endpoint = endpoint;

            return this;
        }

        public Builder<T> errorCode(int errorCode) {
            this.errorCode = errorCode;

            return this;
        }

        public Builder<T> message(Locale locale, String message) {
            if (this.message == null) {
                this.message = new HashMap<>();
            }
            this.message.put(locale.getISO3Language(), message);

            return this;
        }

        public Builder<T> payload(T payload) {
            this.payload = payload;

            return this;
        }

        public ErrorResponse<T> build() {

            return new ErrorResponse<>(application, endpoint, errorCode, message, payload);
        }
    }

    public String getApplication() {
        return application;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public Map<String, String> getMessages() {
        return messages;
    }

    @Nullable
    public T getPayload() {
        return payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorResponse<?> that = (ErrorResponse<?>) o;
        return errorCode == that.errorCode && application.equals(that.application) && endpoint.equals(that.endpoint) && messages.equals(that.messages) && Objects.equals(payload, that.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(application, endpoint, errorCode, messages, payload);
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "application='" + application + '\'' +
                ", endpoint='" + endpoint + '\'' +
                ", errorCode=" + errorCode +
                ", messages=" + messages +
                ", payload=" + payload +
                '}';
    }
}
