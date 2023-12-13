package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.AbstractConverter;
import br.com.eventhorizon.common.conversion.Converter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DoubleToStringConverter extends AbstractConverter<Double, String> {

    private static final class InstanceHolder {
        private static final DoubleToStringConverter instance = new DoubleToStringConverter();
    }

    public static DoubleToStringConverter getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public Class<Double> getSourceType() {
        return Double.class;
    }

    @Override
    public Class<String> getTargetType() {
        return String.class;
    }

    @Override
    public String doConvert(Double value) {
        return value.toString();
    }

    @Override
    public Converter<String, Double> reverse() {
        return StringToDoubleConverter.getInstance();
    }
}
