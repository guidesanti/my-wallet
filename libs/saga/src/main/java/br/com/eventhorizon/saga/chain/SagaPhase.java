package br.com.eventhorizon.saga.chain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SagaPhase {

    START(0, 100, 200),
    BROKER_TRANSACTION(300, 400, 500),
    REPOSITORY_TRANSACTION(600, 700, 800),
    END(900, 1000, 1100);

    private final int before;

    private final int order;

    private final int after;

    public int before() {
        return before;
    }

    public int order() {
        return order;
    }

    public int after() {
        return after;
    }
}
