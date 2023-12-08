package br.com.eventhorizon.mywallet.common.saga.chain;

import br.com.eventhorizon.mywallet.common.saga.SagaOption;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public class SagaOptions {

    private final Map<SagaOption, Object> options;

    public SagaOptions() {
        this.options = new HashMap<>();
        for (SagaOption option : SagaOption.values()) {
            this.options.put(option, option.getDefaultValue());
        }
    }

    public SagaOptions(Map<SagaOption, Object> options) {
        this();
        if (options != null) {
            this.options.putAll(options);
        }
    }

    public static SagaOptions of(Map<SagaOption, Object> options) {
        return new SagaOptions(options);
    }

    public SagaOptions option(@NonNull SagaOption option, @NonNull Object value) {
        this.options.put(option, value);
        return this;
    }

    public <T> T get(@NonNull SagaOption option) {
        return (T) options.getOrDefault(option, option.getDefaultValue());
    }
}
