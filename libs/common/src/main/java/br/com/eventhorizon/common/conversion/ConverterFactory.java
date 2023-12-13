package br.com.eventhorizon.common.conversion;

import br.com.eventhorizon.common.conversion.exception.ConverterNotFoundException;
import br.com.eventhorizon.common.conversion.impl.*;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;
import java.util.*;

public final class ConverterFactory {

//    private static final Set<Pair<?, ?>> availableConverters = new HashSet<>();

    private static final Map<Pair<?, ?>, Converter<?, ?>> converters = new HashMap();

    static {
        converters.put(Pair.of(Boolean.class, String.class), BooleanToStringConverter.getInstance());
        converters.put(Pair.of(Double.class, String.class), DoubleToStringConverter.getInstance());
        converters.put(Pair.of(Integer.class, String.class), IntegerToStringConverter.getInstance());
        converters.put(Pair.of(OffsetDateTime.class, String.class), OffsetDateTimeToStringConverter.getInstance());
        converters.put(Pair.of(String.class, Boolean.class), StringToBooleanConverter.getInstance());
        converters.put(Pair.of(String.class, Double.class), StringToDoubleConverter.getInstance());
        converters.put(Pair.of(String.class, Integer.class), StringToIntegerConverter.getInstance());
        converters.put(Pair.of(String.class, OffsetDateTime.class), StringToOffsetDateTimeConverter.getInstance());
    }

    public static Converter<?, ?> getConverter(Class<?> sourceType, Class<?> targetType) {
        var pair = Pair.of(sourceType, targetType);
        if (converters.containsKey(pair)) {
            return converters.get(pair);
        } else {
            throw new ConverterNotFoundException(sourceType, targetType);
        }
    }

    public static List<Converter<?, ?>> getConverters() {
        return new ArrayList<>(ConverterFactory.converters.values());
    }

    @EqualsAndHashCode
    @RequiredArgsConstructor(staticName = "of")
    private static class Pair<S, T> {

        private final Class<S> sourceType;

        private final Class<T> targetType;
    }
}
