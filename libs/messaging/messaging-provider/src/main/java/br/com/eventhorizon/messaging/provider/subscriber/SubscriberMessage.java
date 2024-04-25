package br.com.eventhorizon.messaging.provider.subscriber;

import br.com.eventhorizon.common.messaging.Message;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(fluent = true, chain = false)
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SubscriberMessage<T> extends Message<T> {

    private final String source;

    protected SubscriberMessage(Builder<?, T> builder) {
        super(builder);
        this.source = builder.source;
    }

    public static <T> Builder<?, T> builder() {
        return new Builder<>();
    }

    public static class Builder<B extends Builder<B, T>, T> extends Message.Builder<B, T> {

        private String source;

        public B source(String source) {
            this.source = source;
            return self();
        }

        @Override
        public SubscriberMessage<T> build() {
            return new SubscriberMessage<>(this);
        }
    }
}
