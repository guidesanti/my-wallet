package br.com.eventhorizon.common.config;

import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface Config {

    default String getValue(@NonNull String name) {
        return getValue(name, String.class);
    }

    <T> T getValue(@NonNull String name, @NonNull Class<T> type);

    default Optional<String> getOptionalValue(@NonNull String name) {
        return getOptionalValue(name, String.class);
    }

    <T> Optional<T> getOptionalValue(@NonNull String name, @NonNull Class<T> type);

    default List<String> getValues(@NonNull String name) {
        return getValues(name, String.class);
    }

    <T> List<T> getValues(@NonNull String name, @NonNull Class<T> type);

    default Optional<List<String>> getOptionalValues(@NonNull String name) {
        return getOptionalValues(name, String.class);
    }

    <T> Optional<List<T>> getOptionalValues(@NonNull String name, @NonNull Class<T> type);
}
