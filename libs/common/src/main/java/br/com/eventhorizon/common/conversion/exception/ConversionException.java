package br.com.eventhorizon.common.conversion.exception;

import lombok.Getter;

@Getter
public class ConversionException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Cannot convert value '%s' of type '%s' to type '%s'";

    private final Object sourceValue;

    private final Class<?> sourceType;

    private final Class<?> targetType;

    public <S, T> ConversionException(S sourceValue, Class<S> sourceType, Class<T> targetType) {
        super(String.format(MESSAGE_TEMPLATE, sourceValue, sourceType, targetType));
        this.sourceValue = sourceValue;
        this.sourceType = sourceType;
        this.targetType = targetType;
    }

    public <S, T> ConversionException(S sourceValue, Class<S> sourceType, Class<T> targetType, Throwable cause) {
        super(String.format(MESSAGE_TEMPLATE, sourceValue, sourceType, targetType), cause);
        this.sourceValue = sourceValue;
        this.sourceType = sourceType;
        this.targetType = targetType;
    }
}
