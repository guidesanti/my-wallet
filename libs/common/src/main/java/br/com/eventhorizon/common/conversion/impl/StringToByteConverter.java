package br.com.eventhorizon.common.conversion.impl;

import br.com.eventhorizon.common.conversion.AbstractConverter;
import br.com.eventhorizon.common.conversion.Converter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringToByteConverter extends AbstractConverter<String, Byte> {

    private static final class InstanceHolder {
        private static final StringToByteConverter instance = new StringToByteConverter();
    }

    public static StringToByteConverter getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<Byte> getTargetType() {
        return Byte.class;
    }

    @Override
    public Byte doConvert(String value) {
        return Byte.valueOf(value);
    }

    @Override
    public Converter<Byte, String> reverse() {
        return ByteToStringConverter.getInstance();
    }
}
