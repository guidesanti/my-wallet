package br.com.eventhorizon.common.config;

import lombok.NonNull;

public interface Config {

    default String getValue(@NonNull String name) {
        return getValue(name, String.class);
    }

    <T> T getValue(@NonNull String name, @NonNull Class<T> type);
}
