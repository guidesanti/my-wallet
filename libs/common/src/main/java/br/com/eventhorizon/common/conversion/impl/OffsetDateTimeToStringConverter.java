package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.Common;
import br.com.eventhorizon.common.conversion.AbstractConverter;
import br.com.eventhorizon.common.conversion.Converter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OffsetDateTimeToStringConverter extends AbstractConverter<OffsetDateTime, String> {

    private static final class InstanceHolder {
        private static final OffsetDateTimeToStringConverter instance = new OffsetDateTimeToStringConverter();
    }

    public static OffsetDateTimeToStringConverter getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public Class<OffsetDateTime> getSourceType() {
        return OffsetDateTime.class;
    }

    @Override
    public Class<String> getTargetType() {
        return String.class;
    }

    @Override
    public String doConvert(OffsetDateTime value) {
        return value.format(Common.DEFAULT_DATE_TIME_FORMATTER);
    }

    @Override
    public Converter<String, OffsetDateTime> reverse() {
        return StringToOffsetDateTimeConverter.getInstance();
    }
}
