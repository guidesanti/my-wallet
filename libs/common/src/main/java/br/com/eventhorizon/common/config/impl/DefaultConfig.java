package br.com.eventhorizon.common.config.impl;

import br.com.eventhorizon.common.config.Config;
import br.com.eventhorizon.common.config.ConfigProvider;
import br.com.eventhorizon.common.config.exception.ConfigNotFoundException;
import br.com.eventhorizon.common.conversion.ConverterFactory;
import lombok.*;

import java.util.List;
import java.util.Optional;

@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultConfig implements Config {

    @Singular
    private final List<ConfigProvider> providers;

    @Override
    public <T> T getValue(@NonNull String name, @NonNull Class<T> type) {
        return getOptionalValue(name, type).orElseThrow(() -> new ConfigNotFoundException(name));
    }

    @Override
    public <T> Optional<T> getOptionalValue(@NonNull String name, @NonNull Class<T> type) {
        return doGetValue(name).map(value -> ConverterFactory.getConverter(String.class, type).convert(value));
    }

    @Override
    public <T> List<T> getValues(@NonNull String name, @NonNull Class<T> type) {
        return getOptionalValues(name, type).orElseThrow(() -> new ConfigNotFoundException(name));
    }

    @Override
    public <T> Optional<List<T>> getOptionalValues(@NonNull String name, @NonNull Class<T> type) {
        return doGetValue(name).map(value -> ConverterFactory.getListConverter(type).convert(value));
    }

    private Optional<String> doGetValue(String name) {
        for (ConfigProvider provider : providers) {
            String value = provider.getValue(name);
            if (value != null) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }
}
