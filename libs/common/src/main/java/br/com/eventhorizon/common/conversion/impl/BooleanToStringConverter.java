package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.AbstractConverter;
import br.com.eventhorizon.common.conversion.Converter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BooleanToStringConverter extends AbstractConverter<Boolean, String> {

    private static final String TRUE = "TRUE";

    private static final String FALSE = "FALSE";

    private static final class InstanceHolder {
        private static final BooleanToStringConverter instance = new BooleanToStringConverter();
    }

    public static BooleanToStringConverter getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public Class<Boolean> getSourceType() {
        return Boolean.class;
    }

    @Override
    public Class<String> getTargetType() {
        return String.class;
    }

    @Override
    public String doConvert(Boolean value) {
        if (value) {
            return TRUE;
        } else {
            return FALSE;
        }
    }

    @Override
    public Converter<String, Boolean> reverse() {
        return StringToBooleanConverter.getInstance();
    }
}
