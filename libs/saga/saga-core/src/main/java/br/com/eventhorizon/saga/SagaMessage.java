package br.com.eventhorizon.saga;

import br.com.eventhorizon.common.messaging.Message;
import lombok.*;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
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

    public SagaMessage(Builder<?, T> builder) {
        super(builder);
        this.idempotenceId = builder.idempotenceId;
        this.traceId = builder.traceId;
        this.source = builder.source;
    }

    public static <T> Builder<?, T> builder() {
        return new Builder<>();
    }

    public static class Builder<B extends Builder<B, T>, T> extends Message.Builder<B, T> {

        private SagaIdempotenceId idempotenceId;

        private String traceId;

        private String source;

        public B idempotenceId(SagaIdempotenceId idempotenceId) {
            this.idempotenceId = idempotenceId;
            return self();
        }

        public B traceId(String traceId) {
            this.traceId = traceId;
            return self();
        }

        public B source(String source) {
            this.source = source;
            return self();
        }

        @Override
        public SagaMessage<T> build() {
            return new SagaMessage<>(this);
        }
    }
}
