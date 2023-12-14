package br.com.eventhorizon.common.conversion;

import br.com.eventhorizon.common.conversion.exception.ConverterNotFoundException;
import br.com.eventhorizon.common.conversion.impl.*;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;
import java.util.*;

public final class ConverterFactory {

    private static final Map<Pair<?, ?>, Converter<?, ?>> converters = new HashMap<>();

    static {
        // Primitive to string converters
        converters.put(Pair.of(Boolean.class, String.class), BooleanToStringConverter.getInstance());
        converters.put(Pair.of(Character.class, String.class), CharToStringConverter.getInstance());
        converters.put(Pair.of(Byte.class, String.class), ByteToStringConverter.getInstance());
        converters.put(Pair.of(Short.class, String.class), ShortToStringConverter.getInstance());
        converters.put(Pair.of(Integer.class, String.class), IntegerToStringConverter.getInstance());
        converters.put(Pair.of(Long.class, String.class), LongToStringConverter.getInstance());
        converters.put(Pair.of(Float.class, String.class), FloatToStringConverter.getInstance());
        converters.put(Pair.of(Double.class, String.class), DoubleToStringConverter.getInstance());

        // String to primitive converters
        converters.put(Pair.of(String.class, Boolean.class), StringToBooleanConverter.getInstance());
        converters.put(Pair.of(String.class, Character.class), StringToCharConverter.getInstance());
        converters.put(Pair.of(String.class, Byte.class), StringToByteConverter.getInstance());
        converters.put(Pair.of(String.class, Short.class), StringToShortConverter.getInstance());
        converters.put(Pair.of(String.class, Integer.class), StringToIntegerConverter.getInstance());
        converters.put(Pair.of(String.class, Long.class), StringToLongConverter.getInstance());
        converters.put(Pair.of(String.class, Float.class), StringToFloatConverter.getInstance());
        converters.put(Pair.of(String.class, Double.class), StringToDoubleConverter.getInstance());

        // Special converters
        converters.put(Pair.of(String.class, String.class), StringToStringConverter.getInstance());
        converters.put(Pair.of(OffsetDateTime.class, String.class), OffsetDateTimeToStringConverter.getInstance());
        converters.put(Pair.of(String.class, OffsetDateTime.class), StringToOffsetDateTimeConverter.getInstance());
        converters.put(Pair.of(Class.class, String.class), ClassToStringConverter.getInstance());
        converters.put(Pair.of(String.class, Class.class), StringToClassConverter.getInstance());
    }

    public static <S, T> Converter<S, T> getConverter(Class<S> sourceType, Class<T> targetType) {
        var pair = Pair.of(sourceType, targetType);
        if (converters.containsKey(pair)) {
            return (Converter<S, T>) converters.get(pair);
        } else {
            throw new ConverterNotFoundException(sourceType, targetType);
        }
    }

    public static <T> Converter<String, List<T>> getListConverter(Class<T> targetType) {
        return new StringToListConverter<>(getConverter(String.class, targetType));
    }

    @EqualsAndHashCode
    @RequiredArgsConstructor(staticName = "of")
    private static class Pair<S, T> {

        private final Class<S> sourceType;

        private final Class<T> targetType;
    }
}
