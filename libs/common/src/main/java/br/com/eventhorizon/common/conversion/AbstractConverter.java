package br.com.eventhorizon.common.conversion;

import br.com.eventhorizon.common.conversion.exception.ConversionException;

public abstract class AbstractConverter<S, T> implements Converter<S, T> {

    @Override
    public T convert(S value) {
        if (value == null) {
            return null;
        }
        try {
            return doConvert(value);
        } catch(ConversionException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ConversionException(value, getSourceType(), getTargetType());
        }
    }

    protected abstract T doConvert(S value);
}
