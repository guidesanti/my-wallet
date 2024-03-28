package br.com.eventhorizon.messaging.provider.subscriber.chain.filter;

import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMessage;
import br.com.eventhorizon.messaging.provider.subscriber.SubscriberPhase;
import br.com.eventhorizon.messaging.provider.subscriber.chain.MessageChain;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class MetricsMessageFilterTest {

    @Test
    public void testOrder() {
        // Given
        var filter = MetricsMessageFilter.builder().build();

        // Then
        assertEquals(SubscriberPhase.METRICS.order(), filter.order());
    }

    @Test
    public void testFilterOnSuccess() {
        // Given
        var content = new Object();
        var message = SubscriberMessage.<Object>builder()
                .source("source")
                .content(content)
                .build();
        var chain = mock(MessageChain.class);
        var filter = MetricsMessageFilter.builder().build();

        // Then
        assertDoesNotThrow(() -> filter.filter(List.of(message), chain));
    }

    @Test
    public void testFilterOnError() throws Exception {
        // Given
        var content = new Object();
        var message = SubscriberMessage.<Object>builder()
                .source("source")
                .content(content)
                .build();
        var chain = mock(MessageChain.class);
        doThrow(Exception.class).when(chain).next(any());
        var filter = MetricsMessageFilter.builder().build();

        // Then
        assertThrows(Exception.class, () -> filter.filter(List.of(message), chain));
    }
}
