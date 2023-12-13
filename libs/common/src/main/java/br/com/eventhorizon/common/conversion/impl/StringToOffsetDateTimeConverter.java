package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.Conventions;
import br.com.eventhorizon.common.conversion.AbstractConverter;
import br.com.eventhorizon.common.conversion.Converter;

import java.time.OffsetDateTime;

public class StringToOffsetDateTimeConverter extends AbstractConverter<String, OffsetDateTime> {

    private static final class InstanceHolder {
        private static final StringToOffsetDateTimeConverter instance = new StringToOffsetDateTimeConverter();
    }

    public static StringToOffsetDateTimeConverter getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<OffsetDateTime> getTargetType() {
        return OffsetDateTime.class;
    }

    @Override
    protected OffsetDateTime doConvert(String value) {
        return OffsetDateTime.parse(value, Conventions.DEFAULT_DATE_TIME_FORMATTER);
    }

    @Override
    public Converter<OffsetDateTime, String> reverse() {
        return OffsetDateTimeToStringConverter.getInstance();
    }
}
