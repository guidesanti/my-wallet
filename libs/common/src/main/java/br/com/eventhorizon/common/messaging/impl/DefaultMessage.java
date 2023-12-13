package br.com.eventhorizon.common.messaging.impl;

import br.com.eventhorizon.common.messaging.Headers;
import br.com.eventhorizon.common.messaging.Message;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder()
@RequiredArgsConstructor(staticName = "of")
public class DefaultMessage<T> implements Message<T> {

    private final Headers headers;

    private final T content;

    @Override
    public Headers headers() {
        return headers;
    }

    @Override
    public T content() {
        return content;
    }
}
