package br.com.eventhorizon.messaging.provider.subscription;

import br.com.eventhorizon.messaging.provider.subscriber.chain.MessageFilter;
import br.com.eventhorizon.messaging.provider.subscriber.handler.MessageHandler;
import br.com.eventhorizon.messaging.provider.subscriber.subscription.DefaultSubscription;
import br.com.eventhorizon.messaging.provider.subscriber.subscription.SubscriptionProperties;
import lombok.experimental.SuperBuilder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class DefaultSubscriptionTest {

    @Test
    public void testBuilderWithNullValues() {
        MessageHandler<Object> handler = mock(MessageHandler.class);

        assertThrows(NullPointerException.class, () -> CustomSubscription.builder().build());
        assertThrows(NullPointerException.class, () -> CustomSubscription.builder()
                .id("id")
                .filters(null)
                .handler(handler)
                .source("source")
                .sourceType(Object.class)
                .properties(new SubscriptionProperties())
                .build());
        assertThrows(NullPointerException.class, () -> CustomSubscription.builder()
                .id("id")
                .filters(Collections.emptyList())
                .handler(null)
                .source("source")
                .sourceType(Object.class)
                .properties(new SubscriptionProperties())
                .build());
        assertThrows(NullPointerException.class, () -> CustomSubscription.builder()
                .id("id")
                .filters(Collections.emptyList())
                .handler(handler)
                .source(null)
                .sourceType(Object.class)
                .properties(new SubscriptionProperties())
                .build());
        assertThrows(NullPointerException.class, () -> CustomSubscription.builder()
                .id("id")
                .filters(Collections.emptyList())
                .handler(handler)
                .source("source")
                .sourceType(null)
                .properties(new SubscriptionProperties())
                .build());
        assertThrows(NullPointerException.class, () -> CustomSubscription.builder()
                .id("id")
                .filters(Collections.emptyList())
                .handler(handler)
                .source("source")
                .sourceType(Object.class)
                .properties(null)
                .build());
    }

    @Test
    public void testBuilderWithDefaultValues() {
        // Given
        MessageHandler<Object> handler = mock(MessageHandler.class);

        // When
        var subscription = CustomSubscription.builder()
                .handler(handler)
                .source("source")
                .sourceType(Object.class)
                .build();

        // Then
        assertNotNull(subscription);
        assertNull(subscription.getId());
        assertEquals("provider-name", subscription.getProviderName());
        assertNotNull(subscription.getFilters());
        assertTrue(subscription.getFilters().isEmpty());
        assertSame(handler, subscription.getHandler());
        assertEquals("source", subscription.getSource());
        assertEquals(Object.class, subscription.getSourceType());
        assertEquals(new SubscriptionProperties(), subscription.getProperties());
    }

    @Test
    public void testBuilderWithCustomValues() {
        // Given
        List<MessageFilter<Object>> filters = new ArrayList<>();
        filters.add(mock(MessageFilter.class));
        MessageHandler<Object> handler = mock(MessageHandler.class);
        var properties = new SubscriptionProperties();

        // When
        var subscription = CustomSubscription.builder()
                .id("id")
                .filters(filters)
                .handler(handler)
                .source("source")
                .sourceType(Object.class)
                .properties(properties)
                .build();

        // Then
        assertNotNull(subscription);
        assertEquals("id", subscription.getId());
        assertEquals("provider-name", subscription.getProviderName());
        assertEquals(filters, subscription.getFilters());
        assertSame(handler, subscription.getHandler());
        assertEquals("source", subscription.getSource());
        assertEquals(Object.class, subscription.getSourceType());
        assertSame(properties, subscription.getProperties());
    }

    @Test
    public void testToString() {
        // Given
        List<MessageFilter<Object>> filters = new ArrayList<>();
        filters.add(mock(MessageFilter.class));
        MessageHandler<Object> handler = mock(MessageHandler.class);
        var properties = new SubscriptionProperties();
        var subscription = CustomSubscription.builder()
                .id("id")
                .filters(filters)
                .handler(handler)
                .source("source")
                .sourceType(Object.class)
                .properties(properties)
                .build();

        // When
        var result = subscription.toString();

        // Then
        assertNotNull(result);
    }

    @SuperBuilder
    private static class CustomSubscription<T> extends DefaultSubscription<T> {

        @Override
        public String getProviderName() {
            return "provider-name";
        }
    }
}
