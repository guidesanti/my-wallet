package br.com.eventhorizon.saga;

import br.com.eventhorizon.common.messaging.Headers;
import br.com.eventhorizon.common.messaging.Message;
import lombok.*;

import java.util.List;
import java.util.Map;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class SagaMessage<T> extends Message<T> {

    private final SagaIdempotenceId idempotenceId;

    private final String traceId;

    private final String source;

    public SagaIdempotenceId idempotenceId() {
        return idempotenceId;
    }

    public String traceId() {
        return traceId;
    }

    public String source() {
        return source;
    }

    public SagaMessage(Builder<T> builder) {
        super(builder);
        this.idempotenceId = builder.idempotenceId;
        this.traceId = builder.traceId;
        this.source = builder.source;
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T> extends Message.Builder<T> {

        private SagaIdempotenceId idempotenceId;

        private String traceId;

        private String source;

        @Override
        public Builder<T> header(@NonNull String name, @NonNull String value) {
            return (Builder<T>) super.header(name, value);
        }

        @Override
        public Builder<T> headers(@NonNull Map<String, List<String>> headers) {
            return (Builder<T>) super.headers(headers);
        }

        @Override
        public Builder<T> headers(@NonNull Headers headers) {
            return (Builder<T>) super.headers(headers);
        }

        @Override
        public Builder<T> content(@NonNull T content) {
            return (Builder<T>) super.content(content);
        }

        public Builder<T> idempotenceId(SagaIdempotenceId idempotenceId) {
            this.idempotenceId = idempotenceId;
            return this;
        }

        public Builder<T> traceId(String traceId) {
            this.traceId = traceId;
            return this;
        }

        public Builder<T> source(String source) {
            this.source = source;
            return this;
        }

        @Override
        public SagaMessage<T> build() {
            return new SagaMessage<>(this);
        }
    }
}
