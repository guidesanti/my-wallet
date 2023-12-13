package br.com.eventhorizon.common.conversion.exception;

import lombok.Getter;

@Getter
public class ConverterNotFoundException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Converter of source type '%s' and target type '%s' not found";

    private final Class<?> sourceType;

    private final Class<?> targetType;

    public ConverterNotFoundException(Class<?> sourceType, Class<?> targetType) {
        super(String.format(MESSAGE_TEMPLATE, sourceType, targetType));
        this.sourceType = sourceType;
        this.targetType = targetType;
    }
}
