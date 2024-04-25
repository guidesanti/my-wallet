package br.com.eventhorizon.messaging.provider.publisher;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PublishingResultTest {

    @Test
    public void testBuilderWithDefaultValues() {
        // Given
        var result = PublishingResult.builder().build();

        // Then
        assertFalse(result.isOk());
    }

    @Test
    public void testBuilderWithCustomValues() {
        // Given
        var result = PublishingResult.builder()
                .ok(true)
                .build();

        // Then
        assertTrue(result.isOk());
    }
}
