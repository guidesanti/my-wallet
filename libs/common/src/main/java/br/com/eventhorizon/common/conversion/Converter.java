package br.com.eventhorizon.common.conversion;

public interface Converter<S, T> {

    Class<S> getSourceType();

    Class<T> getTargetType();

    T convert(S value);

    default Converter<T, S> reverse() {
        throw new UnsupportedOperationException();
    }
}
