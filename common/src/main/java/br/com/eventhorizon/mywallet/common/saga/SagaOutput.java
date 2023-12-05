package br.com.eventhorizon.mywallet.common.saga;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Singular;

import java.util.List;

@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SagaOutput {

    private final SagaResponse response;

    @Singular
    private final List<SagaEvent> events;

    public SagaResponse response() {
        return response;
    }

    public List<SagaEvent> events() {
        return events;
    }
}
