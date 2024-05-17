package br.com.eventhorizon.messaging.provider.subscriber.chain.filter;

import br.com.eventhorizon.common.Common;
import br.com.eventhorizon.common.exception.FailureException;
import br.com.eventhorizon.messaging.provider.Header;
import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMessage;
import br.com.eventhorizon.messaging.provider.subscriber.SubscriberPhase;
import br.com.eventhorizon.messaging.provider.subscriber.chain.MessageFilterChain;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

//    @Test
//    public void testFilterOnSuccessWithIdempotenceIdHeader() {
//        // Given
//        var content = new Object();
//        var message1 = SubscriberMessage.<Object>builder()
//                .header(Header.IDEMPOTENCE_ID.getName(), "idempotence-id1")
//                .source("source")
//                .content(content)
//                .build();
//        var message2 = SubscriberMessage.<Object>builder()
//                .header(Header.IDEMPOTENCE_ID.getName(), "idempotence-id2")
//                .source("source")
//                .content(content)
//                .build();
//        var message3 = SubscriberMessage.<Object>builder()
//                .header(Header.IDEMPOTENCE_ID.getName(), "idempotence-id3")
//                .source("source")
//                .content(content)
//                .build();
//        var message4 = SubscriberMessage.<Object>builder()
//                .source("source")
//                .content(content)
//                .build();
//        var chain = mock(MessageFilterChain.class);
//        var filter = LoggerMessageFilter.builder().build();
//        assertNull(MDC.get(Common.MDCKey.IDEMPOTENCE_ID));
//
//        // When
//        assertDoesNotThrow(() -> filter.filter(List.of(message1, message2, message3, message4), chain));
//
//        // Then
//        assertEquals("idempotence-id1,idempotence-id2,idempotence-id3,unknown", MDC.get(Common.MDCKey.IDEMPOTENCE_ID));
//    }
//
//    @Test
//    public void testFilterOnSuccessWithTraceIdHeader() {
//        // Given
//        var content = new Object();
//        var message1 = SubscriberMessage.<Object>builder()
//                .header(Header.TRACE_ID.getName(), "trace-id1")
//                .source("source")
//                .content(content)
//                .build();
//        var message2 = SubscriberMessage.<Object>builder()
//                .header(Header.TRACE_ID.getName(), "trace-id2")
//                .source("source")
//                .content(content)
//                .build();
//        var message3 = SubscriberMessage.<Object>builder()
//                .header(Header.TRACE_ID.getName(), "trace-id3")
//                .source("source")
//                .content(content)
//                .build();
//        var message4 = SubscriberMessage.<Object>builder()
//                .source("source")
//                .content(content)
//                .build();
//        var chain = mock(MessageFilterChain.class);
//        var filter = LoggerMessageFilter.builder().build();
//        assertNull(MDC.get(Common.MDCKey.TRACE_ID));
//
//        // When
//        assertDoesNotThrow(() -> filter.filter(List.of(message1, message2, message3, message4), chain));
//
//        // Then
//        assertEquals("trace-id1,trace-id2,trace-id3,unknown", MDC.get(Common.MDCKey.TRACE_ID));
//    }
}
