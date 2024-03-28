package br.com.eventhorizon.messaging.provider.subscriber.chain;

import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMessage;
import br.com.eventhorizon.messaging.provider.subscriber.handler.BulkMessageHandler;
import br.com.eventhorizon.messaging.provider.subscriber.handler.MessageHandler;
import br.com.eventhorizon.messaging.provider.subscriber.handler.SingleMessageHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageChainTest {

    @Mock
    private Iterator<MessageFilter<Object>> filters;

    @Mock
    private SingleMessageHandler<Object> singleMessageHandler;

    @Mock
    private BulkMessageHandler<Object> bulkMessageHandler;

    @Test
    public void testConstructor() {
        assertThrows(NullPointerException.class, () -> new MessageChain<>(null, mock(MessageHandler.class)));
        assertThrows(NullPointerException.class, () -> new MessageChain<>(filters, null));
        assertDoesNotThrow(() -> new MessageChain<>(filters, mock(MessageHandler.class)));
    }

    @Test
    public void testNextWithNull() throws Exception {
        // Given
        var chain = new MessageChain<>(filters, mock(MessageHandler.class));

        // When
        chain.next(null);

        // Then
        verify(filters, never()).hasNext();
    }

    @Test
    public void testNextWithEmpty() throws Exception {
        // Given
        var chain = new MessageChain<>(filters, mock(MessageHandler.class));

        // When
        chain.next(Collections.emptyList());

        // Then
        verify(filters, never()).hasNext();
    }

    @Test
    public void testNextWithNonEmpty() throws Exception {
        // Given
        var messages = List.<SubscriberMessage<Object>>of(
                mock(SubscriberMessage.class), mock(SubscriberMessage.class), mock(SubscriberMessage.class));
        doReturn(true).when(filters).hasNext();
        doReturn(mock(MessageFilter.class)).when(filters).next();
        var chain = new MessageChain<>(filters, bulkMessageHandler);

        // When
        chain.next(messages);

        // Then
        verify(filters, times(1)).hasNext();
        verify(bulkMessageHandler, never()).handle(any());
    }

    @Test
    public void testNextWithNonEmptyAndBulkHandler() throws Exception {
        // Given
        var messages = List.<SubscriberMessage<Object>>of(
                mock(SubscriberMessage.class), mock(SubscriberMessage.class), mock(SubscriberMessage.class));
        doReturn(false).when(filters).hasNext();
        var chain = new MessageChain<>(filters, bulkMessageHandler);

        // When
        chain.next(messages);

        // Then
        verify(filters, times(1)).hasNext();
        verify(bulkMessageHandler, times(1)).handle(any());
    }

    @Test
    public void testNextWithNonEmptyAndSingleHandler() throws Exception {
        // Given
        var messages = List.<SubscriberMessage<Object>>of(
                mock(SubscriberMessage.class), mock(SubscriberMessage.class), mock(SubscriberMessage.class));
        doReturn(false).when(filters).hasNext();
        var chain = new MessageChain<>(filters, singleMessageHandler);

        // When
        chain.next(messages);

        // Then
        verify(filters, times(1)).hasNext();
        verify(singleMessageHandler, times(messages.size())).handle(any());
    }
}
