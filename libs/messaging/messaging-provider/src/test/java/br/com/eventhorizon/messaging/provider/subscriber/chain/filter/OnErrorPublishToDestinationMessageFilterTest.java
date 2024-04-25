package br.com.eventhorizon.messaging.provider.subscriber.chain.filter;

import br.com.eventhorizon.common.config.Config;
import br.com.eventhorizon.common.error.ErrorCode;
import br.com.eventhorizon.common.exception.ServerErrorException;
import br.com.eventhorizon.common.messaging.Message;
import br.com.eventhorizon.messaging.provider.Header;
import br.com.eventhorizon.messaging.provider.MessagingProviderError;
import br.com.eventhorizon.messaging.provider.publisher.Publisher;
import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMessage;
import br.com.eventhorizon.messaging.provider.subscriber.SubscriberPhase;
import br.com.eventhorizon.messaging.provider.subscriber.chain.MessageFilterChain;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class OnErrorPublishToDestinationMessageFilterTest {

    private static final List<Header> EXPECTED_HEADERS = List.of(
            Header.PUBLISHED_AT, Header.PUBLISHER, Header.RETRY_COUNT, Header.ERROR_CATEGORY,
            Header.ERROR_CODE, Header.ERROR_MESSAGE, Header.ERROR_STACK_TRACE);

    @Mock
    private Config config;

    @Captor
    private ArgumentCaptor<Message<Object>> captor;

    @Test
    public void testOrder() {
        // Given
        var filter = OnErrorPublishToDestinationMessageFilter.builder()
                .config(config)
                .publisher(mock(Publisher.class))
                .build();

        // Then
        assertEquals(SubscriberPhase.DLQ.order(), filter.order());
    }

    @Test
    public void testFilter() throws Exception {
        // Given
        var content = new Object();
        var traceId = "trace-id";
        var retryCount = "10";
        var message = SubscriberMessage.<Object>builder()
                .source("source")
                .header(Header.TRACE_ID.getName(), traceId)
                .header(Header.RETRY_COUNT.getName(), retryCount)
                .content(content)
                .build();
        var chain = mock(MessageFilterChain.class);
        doThrow(Exception.class).when(chain).next(any());
        var publisher = mock(Publisher.class);
        var filter = new OnErrorPublishToDestinationMessageFilter<>(config, publisher, "destination");

        // When
        filter.filter(List.of(message), chain);

        // Then
        verify(publisher).publishAsync(any(String.class), captor.capture());
        verifyHeaders(captor, traceId, retryCount, content);
    }

    @Test
    public void testFilterWhenPublishToDestinationFail() throws Exception {
        // Given
        var content = new Object();
        var message = SubscriberMessage.<Object>builder()
                .source("source")
                .content(content)
                .build();
        var chain = mock(MessageFilterChain.class);
        doThrow(Exception.class).when(chain).next(any());
        var publisher = mock(Publisher.class);
        doThrow(ServerErrorException.class).when(publisher).publishAsync(any(String.class), any());
        var filter = new OnErrorPublishToDestinationMessageFilter<>(config, publisher, "destination");

        // When
        Exception exception = null;
        try {
            filter.filter(List.of(message), chain);
        } catch (Exception ex) {
            exception = ex;
        }

        // Then
        verify(publisher, times(1)).publishAsync(any(String.class), any());
        assertNotNull(exception);
        assertTrue(exception instanceof ServerErrorException);
        ServerErrorException serverErrorException = (ServerErrorException) exception;
        assertEquals(ErrorCode.Type.LIB, serverErrorException.getError().getCode().getType());
        assertEquals(MessagingProviderError.DOMAIN, serverErrorException.getError().getCode().getDomain());
        assertEquals(MessagingProviderError.MESSAGE_PUBLISH_TO_DESTINATION_FAILED.name(), serverErrorException.getError().getCode().getCode());
    }

    private void verifyHeaders(ArgumentCaptor<Message<Object>> captor, String expectedTraceId, String expectedRetryCount, Object expectedContent) {
        var headers = captor.getValue().headers();
        EXPECTED_HEADERS.stream()
                .map(Header::getName)
                .forEach(headerName -> {
                    System.out.println("Header name: " + headerName);
                    assertTrue(headers.firstValue(headerName).isPresent(), headerName + " header not present");
                    assertEquals(1, headers.values(headerName).size(), headerName + " header with more than one value");
                });

        // Trace ID
        assertTrue(headers.firstValue(Header.TRACE_ID.getName()).isPresent());
        assertEquals(expectedTraceId, headers.firstValue(Header.TRACE_ID.getName()).get(), Header.TRACE_ID.getName() + " header value not expected");

        // Retry count
        assertTrue(headers.firstValue(Header.RETRY_COUNT.getName()).isPresent());
        assertEquals(expectedRetryCount, headers.firstValue(Header.RETRY_COUNT.getName()).get(), Header.RETRY_COUNT.getName() + " header value not expected");

        // Content
        assertSame(expectedContent, captor.getValue().content());
    }
}
