package br.com.eventhorizon.messaging.provider.subscriber;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SubscriberPhase {

    START(0, 1000, 2000),
    METRICS(3000, 4000, 5000),
    TRACING(6000, 7000, 8000),
    DLQ(9000, 10000, 11000),
    RETRY(12000, 13000, 14000),
    END(15000, 16000, 17000);

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
