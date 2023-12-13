package br.com.eventhorizon.common.config.impl;

import br.com.eventhorizon.common.config.Config;
import br.com.eventhorizon.common.config.ConfigProvider;
import br.com.eventhorizon.common.config.exception.ConfigNotFoundException;
import br.com.eventhorizon.common.conversion.Converter;
import br.com.eventhorizon.common.conversion.ConverterFactory;
import lombok.*;

import java.util.List;

@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultConfig implements Config {

    @Singular
    private final List<ConfigProvider> providers;

    @Override
    public <T> T getValue(@NonNull String name, @NonNull Class<T> type) {
        Converter<String, T> converter = null;
        if (String.class != type) {
            converter = (Converter<String, T>) ConverterFactory.getConverter(String.class, type);
        }
        for (ConfigProvider provider : providers) {
            String value = provider.getValue(name);
            if (value != null) {
                return converter == null ? (T) value : converter.convert(value);
            }
        }
        throw new ConfigNotFoundException(name);
    }
}
