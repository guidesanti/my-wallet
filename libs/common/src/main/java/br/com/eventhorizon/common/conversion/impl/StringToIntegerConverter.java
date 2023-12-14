package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.AbstractConverter;
import br.com.eventhorizon.common.conversion.Converter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringToIntegerConverter extends AbstractConverter<String, Integer> {

    private static final class InstanceHolder {
        private static final StringToIntegerConverter instance = new StringToIntegerConverter();
    }

    public static StringToIntegerConverter getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<Integer> getTargetType() {
        return Integer.class;
    }

    @Override
    public Integer doConvert(String value) {
        return Integer.valueOf(value);
    }

    @Override
    public Converter<Integer, String> reverse() {
        return IntegerToStringConverter.getInstance();
    }
}
