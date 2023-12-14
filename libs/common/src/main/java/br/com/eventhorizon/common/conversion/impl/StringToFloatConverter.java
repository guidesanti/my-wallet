package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.AbstractConverter;
import br.com.eventhorizon.common.conversion.Converter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringToFloatConverter extends AbstractConverter<String, Float> {

    private static final class InstanceHolder {
        private static final StringToFloatConverter instance = new StringToFloatConverter();
    }

    public static StringToFloatConverter getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<Float> getTargetType() {
        return Float.class;
    }

    @Override
    public Float doConvert(String value) {
        return Float.valueOf(value);
    }

    @Override
    public Converter<Float, String> reverse() {
        return FloatToStringConverter.getInstance();
    }
}
