package br.com.eventhorizon.saga;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SagaOptionTest {

    @Test
    public void testLength() {
        // This is to remember to add new values to this test class
        assertEquals(5, SagaOption.values().length);
    }

    @Test
    public void testName() {
        assertEquals("transaction.collection.name", SagaOption.TRANSACTION_COLLECTION_NAME.getName());
        assertEquals("transaction.ttl", SagaOption.TRANSACTION_TTL.getName());
        assertEquals("response.collection.name", SagaOption.RESPONSE_COLLECTION_NAME.getName());
        assertEquals("event.collection.name", SagaOption.EVENT_COLLECTION_NAME.getName());
        assertEquals("event.republish.enabled", SagaOption.EVENT_REPUBLISH_ENABLED.getName());
    }

    @Test
    public void testDefaultValue() {
        assertEquals("saga-transactions", SagaOption.TRANSACTION_COLLECTION_NAME.getDefaultValue());
        assertEquals(86400L, SagaOption.TRANSACTION_TTL.getDefaultValue());
        assertEquals("saga-responses", SagaOption.RESPONSE_COLLECTION_NAME.getDefaultValue());
        assertEquals("saga-events", SagaOption.EVENT_COLLECTION_NAME.getDefaultValue());
        assertEquals(true, SagaOption.EVENT_REPUBLISH_ENABLED.getDefaultValue());
    }
}
