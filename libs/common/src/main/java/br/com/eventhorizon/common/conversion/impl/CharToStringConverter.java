package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.AbstractConverter;
import br.com.eventhorizon.common.conversion.Converter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CharToStringConverter extends AbstractConverter<Character, String> {

    private static final class InstanceHolder {
        private static final CharToStringConverter instance = new CharToStringConverter();
    }

    public static CharToStringConverter getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public Class<Character> getSourceType() {
        return Character.class;
    }

    @Override
    public Class<String> getTargetType() {
        return String.class;
    }

    @Override
    public String doConvert(Character value) {
        return value.toString();
    }

    @Override
    public Converter<String, Character> reverse() {
        return StringToCharConverter.getInstance();
    }
}
