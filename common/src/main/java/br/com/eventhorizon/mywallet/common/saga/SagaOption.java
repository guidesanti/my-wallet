package br.com.eventhorizon.mywallet.common.saga;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SagaOption {

    TRANSACTION_COLLECTION_NAME("transaction.collection.name", "saga-transactions", "The name of the collection/table to store SAGA transactions on database"),

    TRANSACTION_TTL("transaction.ttl", 86400L, "Saga transaction time to live in seconds. The time a saga transactions and its respective response and events will be kept in database before being deleted."),

    RESPONSE_COLLECTION_NAME("response.collection.name", "saga-responses", "The name of the collection/table to store SAGA responses on database"),

    EVENT_COLLECTION_NAME("event.collection.name", "saga-events", "The name of the collection/table to store SAGA events on database"),

    EVENT_REPUBLISH_ENABLED("event.republish.enabled", Boolean.TRUE, "If true, events that have already been published will get republished if there source message is received more than once.");

    private final String name;

    private final Object defaultValue;

    private final String description;
}
