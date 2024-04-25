package br.com.eventhorizon.messaging.provider.subscriber.chain.filter;

import br.com.eventhorizon.common.exception.ServerErrorException;
import br.com.eventhorizon.messaging.provider.MessagingProviderError;
import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMessage;
import br.com.eventhorizon.messaging.provider.subscriber.SubscriberPhase;
import br.com.eventhorizon.messaging.provider.subscriber.chain.MessageFilterChain;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class LoggerMessageFilterTest {

    @Test
    public void testOrder() {
        // Given
        var filter = LoggerMessageFilter.builder().build();

        // Then
        assertEquals(SubscriberPhase.START.before(), filter.order());
    }

    @Test
    public void testFilterOnSuccess() {
        // Given
        var content = new Object();
        var message = SubscriberMessage.<Object>builder()
                .source("source")
                .content(content)
                .build();
        var chain = mock(MessageFilterChain.class);
        var filter = LoggerMessageFilter.builder().build();

        // Then
        assertDoesNotThrow(() -> filter.filter(List.of(message), chain));
    }

    @Test
    public void testFilterOnServerError() throws Exception {
        // Given
        var content = new Object();
        var message = SubscriberMessage.<Object>builder()
                .source("source")
                .content(content)
                .build();
        var chain = mock(MessageFilterChain.class);
        doThrow(new ServerErrorException(MessagingProviderError.UNEXPECTED_ERROR.getCode(), "")).when(chain).next(any());
        var filter = LoggerMessageFilter.builder().build();

        // Then
        assertDoesNotThrow(() -> filter.filter(List.of(message), chain));
    }

    @Test
    public void testFilterOnUnexpectedError() throws Exception {
        // Given
        var content = new Object();
        var message = SubscriberMessage.<Object>builder()
                .source("source")
                .content(content)
                .build();
        var chain = mock(MessageFilterChain.class);
        doThrow(Exception.class).when(chain).next(any());
        var filter = LoggerMessageFilter.builder().build();

        // Then
        assertDoesNotThrow(() -> filter.filter(List.of(message), chain));
    }
}
