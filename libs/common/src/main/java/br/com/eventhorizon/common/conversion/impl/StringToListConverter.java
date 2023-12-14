package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.AbstractConverter;
import br.com.eventhorizon.common.conversion.Converter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class StringToListConverter<T> extends AbstractConverter<String, List<T>> {

    private static final String SEPARATOR = ",";

    private final Converter<String, T> innerConverter;

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<List<T>> getTargetType() {
        throw new UnsupportedOperationException("Cannot get the class of List<T>");
    }

    @Override
    public List<T> doConvert(String value) {
        var values = value.trim().split(SEPARATOR);
        return Arrays.stream(values).map(innerConverter::convert).toList();
    }
}
