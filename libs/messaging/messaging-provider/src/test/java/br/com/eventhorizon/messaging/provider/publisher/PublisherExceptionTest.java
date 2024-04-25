package br.com.eventhorizon.messaging.provider.publisher;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PublisherExceptionTest {

    @Test
    public void test() {
        // Given
        var exception1 = new PublisherException("Error message");
        var exception2 = new PublisherException("Error message", new RuntimeException("Second error message"));

        // Then
        assertEquals("Error message", exception1.getMessage());
        assertEquals("Error message", exception2.getMessage());
        assertEquals("Second error message", exception2.getCause().getMessage());
    }
}
