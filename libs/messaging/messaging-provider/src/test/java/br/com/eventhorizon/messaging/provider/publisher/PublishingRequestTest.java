package br.com.eventhorizon.messaging.provider.publisher;

import br.com.eventhorizon.common.messaging.Message;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class PublishingRequestTest {

    @Test
    public void testBuilderWithNullValues() {
        assertThrows(NullPointerException.class, () -> PublishingRequest.builder().build());
        assertThrows(NullPointerException.class, () -> PublishingRequest.builder()
                .message(Message.builder().build())
                .build());
        assertThrows(NullPointerException.class, () -> PublishingRequest.builder()
                .destination(null)
                .message(Message.builder().build())
                .build());
        assertThrows(NullPointerException.class, () -> PublishingRequest.builder()
                .destination("")
                .build());
        assertThrows(NullPointerException.class, () -> PublishingRequest.builder()
                .destination("")
                .message(null)
                .build());
    }

    @Test
    public void testBuilderWithDefaultValues() {
        // Given
        var message = Message.builder().build();
        var request = PublishingRequest.builder()
                .destination("destination")
                .message(message)
                .build();

        // Then
        assertEquals("destination", request.getDestination());
        assertNull(request.getOrderingKey());
        assertEquals(Duration.ZERO, request.getDelay());
        assertSame(message, request.getMessage());
    }

    @Test
    public void testBuilderWithCustomValues() {
        // Given
        var message = Message.builder().build();
        var request = PublishingRequest.builder()
                .destination("destination")
                .orderingKey("ordering-key")
                .delay(Duration.ofMillis(1))
                .message(message)
                .build();

        // Then
        assertEquals("destination", request.getDestination());
        assertEquals("ordering-key", request.getOrderingKey());
        assertEquals(Duration.ofMillis(1), request.getDelay());
        assertSame(message, request.getMessage());
    }
}
