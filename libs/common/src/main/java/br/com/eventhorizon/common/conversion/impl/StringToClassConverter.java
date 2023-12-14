package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.AbstractConverter;
import br.com.eventhorizon.common.conversion.Converter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringToClassConverter extends AbstractConverter<String, Class> {

    private static final class InstanceHolder {
        private static final StringToClassConverter instance = new StringToClassConverter();
    }

    public static StringToClassConverter getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<Class> getTargetType() {
        return Class.class;
    }

    @Override
    public Class doConvert(String value) {
        try {
            return Class.forName(value);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Converter<Class, String> reverse() {
        return ClassToStringConverter.getInstance();
    }
}
