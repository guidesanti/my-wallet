package br.com.eventhorizon.messaging.provider.subscriber;

import br.com.eventhorizon.common.messaging.Message;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubscriberMessage<T> extends Message<T> {

    public SubscriberMessage(Builder<T> builder) {
        super(builder);
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T> extends Message.Builder<T> {

        @Override
        public SubscriberMessage<T> build() {
            return new SubscriberMessage<>(this);
        }
    }
}
