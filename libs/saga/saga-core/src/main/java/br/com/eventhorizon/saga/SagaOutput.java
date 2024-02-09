package br.com.eventhorizon.saga;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Singular;

import java.util.List;

@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SagaOutput<R> {

    private final SagaResponse<R> response;

    @Singular
    private final List<SagaEvent<?>> events;

    public SagaResponse<R> response() {
        return response;
    }

    public List<SagaEvent<?>> events() {
        return events;
    }
}
