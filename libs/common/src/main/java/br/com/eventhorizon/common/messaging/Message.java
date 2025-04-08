package br.com.eventhorizon.common.messaging;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.*;

@Accessors(fluent = true, chain = false)
@Getter
@ToString
@EqualsAndHashCode
public class Message<T> {

    @NonNull
    private final Attributes attributes;

    @NonNull
    private final Headers headers;

    @NonNull
    private final T content;

    protected Message(Builder<?, T> builder) {
        this.attributes = builder.attributesBuilder.build();
        this.headers = builder.headersBuilder.build();
        this.content = builder.content;
    }

    public static <T> Builder<?, T> builder() {
        return new Builder<>();
    }

    public static class Builder<B extends Builder<B, T>, T> {

        private final Attributes.Builder attributesBuilder = Attributes.builder();

        private final Headers.Builder headersBuilder = Headers.builder();

        private T content;

        protected B self() {
            return (B) this;
        }

        public B copy(@NonNull Message<T> message) {
            if (message.attributes != null) {
                attributesBuilder.attributes(message.attributes);
            }
            if (message.headers != null) {
                headersBuilder.headers(message.headers);
            }
            if (message.content != null) {
                content = message.content;
            }
            return self();
        }

        public B attributes(@NonNull Attributes attributes) {
            attributesBuilder.attributes(attributes);
            return self();
        }

        public B attributes(@NonNull Map<String, String> attributes) {
            attributesBuilder.attributes(attributes);
            return self();
        }

        public B attribute(@NonNull String name, @NonNull String value) {
            attributesBuilder.attribute(name, value);
            return self();
        }

        public B headers(@NonNull Headers headers) {
            headersBuilder.headers(headers);
            return self();
        }

        public B headers(@NonNull Map<String, List<String>> headers) {
            headersBuilder.headers(headers);
            return self();
        }

        public B header(@NonNull String name, @NonNull String value) {
            headersBuilder.header(name, Collections.singletonList(value));
            return self();
        }

        public B header(@NonNull String name, @NonNull List<String> values) {
            headersBuilder.header(name, values);
            return self();
        }

        public B content(@NonNull T content) {
            this.content = content;
            return self();
        }

        public Message<T> build() {
            return new Message<>(this);
        }
    }
}
