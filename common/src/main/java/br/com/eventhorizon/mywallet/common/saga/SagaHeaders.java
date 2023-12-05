package br.com.eventhorizon.mywallet.common.saga;

import br.com.eventhorizon.mywallet.common.message.Headers;
import br.com.eventhorizon.mywallet.common.message.impl.DefaultHeaders;
import lombok.NonNull;

import java.util.*;

public class SagaHeaders implements Headers {

    public static final SagaHeaders EMPTY_SAGA_HEADERS = new SagaHeaders(DefaultHeaders.emptyHeaders());

    private final Headers headers;

    private SagaHeaders(Headers headers) {
        this.headers = headers;
    }

    @Override
    public Set<String> names() {
        return headers.names();
    }

    @Override
    public List<String> values(String name) {
        return headers.values(name);
    }

    public static SagaHeaders emptySagaHeaders() {
        return EMPTY_SAGA_HEADERS;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final DefaultHeaders.Builder builder;

        private Builder() {
            this.builder = DefaultHeaders.builder();
        }

        public Builder headers(Map<String, List<String>> headers) {
            if (headers != null) {
                this.builder.headers(headers);
            }
            return this;
        }

        public Builder header(@NonNull String name, @NonNull String value) {
            this.builder.header(name, value);
            return this;
        }

        public SagaHeaders build() {
            return new SagaHeaders(builder.build());
        }
    }
}
