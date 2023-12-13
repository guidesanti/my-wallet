package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.AbstractConverter;
import br.com.eventhorizon.common.conversion.exception.ConversionException;
import br.com.eventhorizon.common.conversion.Converter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringToBooleanConverter extends AbstractConverter<String, Boolean> {

    private static final class InstanceHolder {
        private static final StringToBooleanConverter instance = new StringToBooleanConverter();
    }

    public static StringToBooleanConverter getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<Boolean> getTargetType() {
        return Boolean.class;
    }

    @Override
    public Boolean doConvert(String value) {
        if (value.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        } else if (value.equalsIgnoreCase("false")) {
            return Boolean.FALSE;
        } else {
            throw new ConversionException(value, String.class, Boolean.class);
        }
    }

    @Override
    public Converter<Boolean, String> reverse() {
        return BooleanToStringConverter.getInstance();
    }
}
