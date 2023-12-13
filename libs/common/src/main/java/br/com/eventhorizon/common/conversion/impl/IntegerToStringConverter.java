package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.AbstractConverter;
import br.com.eventhorizon.common.conversion.Converter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IntegerToStringConverter extends AbstractConverter<Integer, String> {

    private static final class InstanceHolder {
        private static final IntegerToStringConverter instance = new IntegerToStringConverter();
    }

    public static IntegerToStringConverter getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public Class<Integer> getSourceType() {
        return Integer.class;
    }

    @Override
    public Class<String> getTargetType() {
        return String.class;
    }

    @Override
    public String doConvert(Integer value) {
        return value.toString();
    }

    @Override
    public Converter<String, Integer> reverse() {
        return StringToIntegerConverter.getInstance();
    }
}
