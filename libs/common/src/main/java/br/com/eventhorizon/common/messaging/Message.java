package br.com.eventhorizon.common.messaging;

import lombok.*;

import java.util.*;

@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Message<T> {

    private final Headers headers;

    private final T content;

    public Message(Builder<T> builder) {
        this.headers = builder.headersBuilder.build();
        this.content = builder.content;
    }

    public Headers headers() {
        return headers;
    }

    public T content() {
        return content;
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T> {

        private T content;

        private final Headers.Builder headersBuilder = Headers.builder();

        public Builder<T> copy(@NonNull Message<T> message) {
            if (message.headers != null) {
                headersBuilder.headers(message.headers);
            }
            if (message.content != null) {
                content = message.content;
            }
            return this;
        }

        public Builder<T> header(@NonNull String name, @NonNull String value) {
            headersBuilder.header(name, Collections.singletonList(value));
            return this;
        }

        public Builder<T> headers(@NonNull Map<String, List<String>> headers) {
            headersBuilder.headers(headers);
            return this;
        }

        public Builder<T> headers(@NonNull Headers headers) {
            headers.names().forEach(name -> this.headersBuilder.header(name, headers.values(name)));
            return this;
        }

        public Builder<T> content(@NonNull T content) {
            this.content = content;
            return this;
        }

        public Message<T> build() {
            return new Message<>(this);
        }
    }
}
