package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.AbstractConverter;
import br.com.eventhorizon.common.conversion.Converter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringToLongConverter extends AbstractConverter<String, Long> {

    private static final class InstanceHolder {
        private static final StringToLongConverter instance = new StringToLongConverter();
    }

    public static StringToLongConverter getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<Long> getTargetType() {
        return Long.class;
    }

    @Override
    public Long doConvert(String value) {
        return Long.valueOf(value);
    }

    @Override
    public Converter<Long, String> reverse() {
        return LongToStringConverter.getInstance();
    }
}
