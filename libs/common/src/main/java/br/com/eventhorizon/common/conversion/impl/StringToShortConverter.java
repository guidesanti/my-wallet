package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.AbstractConverter;
import br.com.eventhorizon.common.conversion.Converter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringToShortConverter extends AbstractConverter<String, Short> {

    private static final class InstanceHolder {
        private static final StringToShortConverter instance = new StringToShortConverter();
    }

    public static StringToShortConverter getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<Short> getTargetType() {
        return Short.class;
    }

    @Override
    public Short doConvert(String value) {
        return Short.valueOf(value);
    }

    @Override
    public Converter<Short, String> reverse() {
        return ShortToStringConverter.getInstance();
    }
}
