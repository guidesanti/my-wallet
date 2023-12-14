package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.AbstractConverter;
import br.com.eventhorizon.common.conversion.Converter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringToStringConverter extends AbstractConverter<String, String> {

    private static final class InstanceHolder {
        private static final StringToStringConverter instance = new StringToStringConverter();
    }

    public static StringToStringConverter getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<String> getTargetType() {
        return String.class;
    }

    @Override
    public String doConvert(String value) {
        return value;
    }

    @Override
    public Converter<String, String> reverse() {
        return this;
    }
}
