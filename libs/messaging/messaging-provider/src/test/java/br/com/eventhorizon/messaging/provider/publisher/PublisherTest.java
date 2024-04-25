package br.com.eventhorizon.messaging.provider.publisher;

import br.com.eventhorizon.common.messaging.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PublisherTest {

    @Spy
    private Publisher publisher = new CustomPublisher();

    @Test
    public void testPublishAsync1() {
        // Given
        var content = new Object();

        // When
        publisher.publishAsync("destination", content);

        // Then
        verify(publisher, times(1)).publishAsync("destination", content);
        verify(publisher, times(1)).publishAsync(anyString(), any());
        verify(publisher, times(1)).publishAsync(any());
    }

    @Test
    public void testPublishAsync2() {
        // Given
        var message = Message.builder().build();

        // When
        publisher.publishAsync("destination", message);

        // Then
        verify(publisher, never()).publishAsync(anyString(), any(Object.class));
        verify(publisher, times(1)).publishAsync("destination", message);
        verify(publisher, times(1)).publishAsync(any());
    }

    @Test
    public void testPublishAsync3() {
        // Given
        var request = PublishingRequest.builder()
                .destination("")
                .message(Message.builder().build())
                .build();

        // When
        publisher.publishAsync(request);

        // Then
        verify(publisher, never()).publishAsync(anyString(), any(Object.class));
        verify(publisher, never()).publishAsync(anyString(), ArgumentMatchers.any());
        verify(publisher, times(1)).publishAsync(request);
    }

    @Test
    public void testBeginTransaction() {
        assertThrows(UnsupportedOperationException.class, () -> publisher.beginTransaction());
    }

    @Test
    public void testCommitTransaction() {
        assertThrows(UnsupportedOperationException.class, () -> publisher.commitTransaction());
    }

    @Test
    public void testExecuteInTransaction() {
        assertThrows(UnsupportedOperationException.class, () -> publisher.executeInTransaction(any()));
    }

    private static class CustomPublisher implements Publisher {

        @Override
        public <T> Future<PublishingResult> publishAsync(PublishingRequest<T> request) {
            return null;
        }
    }
}
