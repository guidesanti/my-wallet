package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.AbstractConverter;
import br.com.eventhorizon.common.conversion.Converter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ByteToStringConverter extends AbstractConverter<Byte, String> {

    private static final class InstanceHolder {
        private static final ByteToStringConverter instance = new ByteToStringConverter();
    }

    public static ByteToStringConverter getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public Class<Byte> getSourceType() {
        return Byte.class;
    }

    @Override
    public Class<String> getTargetType() {
        return String.class;
    }

    @Override
    public String doConvert(Byte value) {
        return value.toString();
    }

    @Override
    public Converter<String, Byte> reverse() {
        return StringToByteConverter.getInstance();
    }
}
