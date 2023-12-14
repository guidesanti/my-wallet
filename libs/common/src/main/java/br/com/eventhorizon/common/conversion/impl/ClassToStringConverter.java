package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.AbstractConverter;
import br.com.eventhorizon.common.conversion.Converter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClassToStringConverter extends AbstractConverter<Class, String> {

    private static final class InstanceHolder {
        private static final ClassToStringConverter instance = new ClassToStringConverter();
    }

    public static ClassToStringConverter getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public Class<Class> getSourceType() {
        return Class.class;
    }

    @Override
    public Class<String> getTargetType() {
        return String.class;
    }

    @Override
    public String doConvert(Class value) {
        return value.getName();
    }

    @Override
    public Converter<String, Class> reverse() {
        return StringToClassConverter.getInstance();
    }
}
