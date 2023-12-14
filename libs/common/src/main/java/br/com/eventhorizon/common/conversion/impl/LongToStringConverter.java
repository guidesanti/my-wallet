package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.AbstractConverter;
import br.com.eventhorizon.common.conversion.Converter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LongToStringConverter extends AbstractConverter<Long, String> {

    private static final class InstanceHolder {
        private static final LongToStringConverter instance = new LongToStringConverter();
    }

    public static LongToStringConverter getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public Class<Long> getSourceType() {
        return Long.class;
    }

    @Override
    public Class<String> getTargetType() {
        return String.class;
    }

    @Override
    public String doConvert(Long value) {
        return value.toString();
    }

    @Override
    public Converter<String, Long> reverse() {
        return StringToLongConverter.getInstance();
    }
}
