package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.AbstractConverter;
import br.com.eventhorizon.common.conversion.Converter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringToDoubleConverter extends AbstractConverter<String, Double> {

    private static final class InstanceHolder {
        private static final StringToDoubleConverter instance = new StringToDoubleConverter();
    }

    public static StringToDoubleConverter getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<Double> getTargetType() {
        return Double.class;
    }

    @Override
    public Double doConvert(String value) {
        return Double.parseDouble(value);
    }

    @Override
    public Converter<Double, String> reverse() {
        return DoubleToStringConverter.getInstance();
    }
}
