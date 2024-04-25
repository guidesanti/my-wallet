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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OnErrorPublishToSourceMessageFilterTest {

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
        var filter = OnErrorPublishToSourceMessageFilter.builder()
                .config(config)
                .publisher(mock(Publisher.class))
                .maxRetries(3)
                .build();

        // Then
        assertEquals(SubscriberPhase.RETRY.order(), filter.order());
    }

    @Test
    public void testFilterWhenNoRetryCountHeaderIsPresent() throws Exception {
        // Given
        var content = new Object();
        var message = SubscriberMessage.<Object>builder()
                .source("source")
                .content(content)
                .build();
        var chain = mock(MessageFilterChain.class);
        doThrow(Exception.class).when(chain).next(any());
        var publisher = mock(Publisher.class);
        var filter = new OnErrorPublishToSourceMessageFilter<>(config, publisher, 3);

        // When
        filter.filter(List.of(message), chain);

        // Then
        verify(publisher).publishAsync(any(String.class), captor.capture());
        verifyHeaders(captor, 1, content);
    }

    @ParameterizedTest
    @CsvSource({
            "3,0,1", "3,1,2", "3,2,3", "5,0,1", "5,1,2", "5,2,3", "5,3,4", "5,4,5"
    })
    public void testFilterWhenRetryCountIsPresent(
            int maxRetries, int currentRetryCount, int expectedRetryCount) throws Exception {
        // Given
        var content = new Object();
        var message = SubscriberMessage.<Object>builder()
                .source("source")
                .header(Header.RETRY_COUNT.getName(), String.valueOf(currentRetryCount))
                .content(content)
                .build();
        var chain = mock(MessageFilterChain.class);
        doThrow(Exception.class).when(chain).next(any());
        var publisher = mock(Publisher.class);
        var filter = new OnErrorPublishToSourceMessageFilter<>(config, publisher, maxRetries);

        // When
        filter.filter(List.of(message), chain);

        // Then
        verify(publisher).publishAsync(any(String.class), captor.capture());
        verifyHeaders(captor, expectedRetryCount, content);
    }

    @Test
    public void testFilterWhenMaxRetriesHasBeenExceeded() throws Exception {
        // Given
        var content = new Object();
        var message = SubscriberMessage.<Object>builder()
                .source("source")
                .header(Header.RETRY_COUNT.getName(), "3")
                .content(content)
                .build();
        var chain = mock(MessageFilterChain.class);
        doThrow(Exception.class).when(chain).next(any());
        var publisher = mock(Publisher.class);
        var filter = new OnErrorPublishToSourceMessageFilter<>(config, publisher, 3);

        // When
        assertThrows(Exception.class, () -> filter.filter(List.of(message), chain));

        // Then
        verify(publisher, never()).publishAsync(any(String.class), any());
    }

    @Test
    public void testFilterWhenRetryCountHasInvalidValue() throws Exception {
        // Given
        var content = new Object();
        var message = SubscriberMessage.<Object>builder()
                .source("source")
                .header(Header.RETRY_COUNT.getName(), "string")
                .content(content)
                .build();
        var chain = mock(MessageFilterChain.class);
        doThrow(Exception.class).when(chain).next(any());
        var publisher = mock(Publisher.class);
        var filter = new OnErrorPublishToSourceMessageFilter<>(config, publisher, 3);

        // When
        filter.filter(List.of(message), chain);

        // Then
        verify(publisher).publishAsync(any(String.class), captor.capture());
        verifyHeaders(captor, 1, content);
    }

    @Test
    public void testFilterWhenPublishToSourceFail() throws Exception {
        // Given
        var content = new Object();
        var message = SubscriberMessage.<Object>builder()
                .source("source")
                .header(Header.RETRY_COUNT.getName(), "0")
                .content(content)
                .build();
        var chain = mock(MessageFilterChain.class);
        doThrow(Exception.class).when(chain).next(any());
        var publisher = mock(Publisher.class);
        doThrow(ServerErrorException.class).when(publisher).publishAsync(any(String.class), any());
        var filter = new OnErrorPublishToSourceMessageFilter<>(config, publisher, 3);

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
        assertEquals(MessagingProviderError.MESSAGE_PUBLISH_TO_SOURCE_FAILED.name(), serverErrorException.getError().getCode().getCode());
    }

    private void verifyHeaders(ArgumentCaptor<Message<Object>> captor, int expectedRetryCount, Object content) {
        var headers = captor.getValue().headers();
        EXPECTED_HEADERS.stream()
                .map(Header::getName)
                .forEach(headerName -> {
                    System.out.println("Header name: " + headerName);
                    assertTrue(headers.firstValue(headerName).isPresent(), headerName + " header not present");
                    assertEquals(1, headers.values(headerName).size(), headerName + " header with more than one value");
                });
        assertTrue(headers.firstValue(Header.RETRY_COUNT.getName()).isPresent());
        assertEquals(String.valueOf(expectedRetryCount), headers.firstValue(Header.RETRY_COUNT.getName()).get(), Header.RETRY_COUNT.getName() + " header value not expected");
        assertSame(content, captor.getValue().content());
    }
}
