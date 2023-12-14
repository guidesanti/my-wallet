package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.AbstractConverter;
import br.com.eventhorizon.common.conversion.Converter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FloatToStringConverter extends AbstractConverter<Float, String> {

    private static final class InstanceHolder {
        private static final FloatToStringConverter instance = new FloatToStringConverter();
    }

    public static FloatToStringConverter getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public Class<Float> getSourceType() {
        return Float.class;
    }

    @Override
    public Class<String> getTargetType() {
        return String.class;
    }

    @Override
    public String doConvert(Float value) {
        return value.toString();
    }

    @Override
    public Converter<String, Float> reverse() {
        return StringToFloatConverter.getInstance();
    }
}
