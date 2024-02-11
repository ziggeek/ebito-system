package com.ebito.exceptionhandler.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.lang.Nullable;

import java.util.Objects;

/**
 * Класс для описания ошибок валидации
 */
public class Validation {

    /**
     * Наименование невалидного поля
     */
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String field;

    /**
     * Причина возникнования ошибки
     */
    private final String message;

    public Validation(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Validation that = (Validation) o;
        return field.equals(that.field) && message.equals(that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, message);
    }

    @Override
    public String toString() {
        return "Validation{" +
                "field='" + field + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
