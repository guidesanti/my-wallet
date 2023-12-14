package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.AbstractConverter;
import br.com.eventhorizon.common.conversion.Converter;
import br.com.eventhorizon.common.conversion.exception.ConversionException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringToCharConverter extends AbstractConverter<String, Character> {

    private static final class InstanceHolder {
        private static final StringToCharConverter instance = new StringToCharConverter();
    }

    public static StringToCharConverter getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<Character> getTargetType() {
        return Character.class;
    }

    @Override
    public Character doConvert(String value) {
        if (value.length() > 1) {
            throw new ConversionException(value, String.class, Character.class);
        }
        return value.charAt(0);
    }

    @Override
    public Converter<Character, String> reverse() {
        return CharToStringConverter.getInstance();
    }
}
