package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.AbstractConverter;
import br.com.eventhorizon.common.conversion.Converter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShortToStringConverter extends AbstractConverter<Short, String> {

    private static final class InstanceHolder {
        private static final ShortToStringConverter instance = new ShortToStringConverter();
    }

    public static ShortToStringConverter getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public Class<Short> getSourceType() {
        return Short.class;
    }

    @Override
    public Class<String> getTargetType() {
        return String.class;
    }

    @Override
    public String doConvert(Short value) {
        return value.toString();
    }

    @Override
    public Converter<String, Short> reverse() {
        return StringToShortConverter.getInstance();
    }
}
