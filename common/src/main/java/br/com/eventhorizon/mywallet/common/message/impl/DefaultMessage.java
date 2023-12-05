package br.com.eventhorizon.mywallet.common.message.impl;

import br.com.eventhorizon.mywallet.common.message.Headers;
import br.com.eventhorizon.mywallet.common.message.Message;
import lombok.Builder;
import lombok.Data;
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
