package br.com.eventhorizon.mywallet.common.saga;

import br.com.eventhorizon.mywallet.common.saga.content.SagaContent;
import org.junit.jupiter.api.Test;

import static com.mongodb.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

public class SagaResponseTest {

    @Test
    public void testCreateSagaResponse() {
        // This should not compile, since constructor is private
        // var sagaResponse = new SagaResponse();

        var idempotenceId = SagaIdempotenceId.of("20231020GGGGGGGGGG000000000000000002000");
        var content = new Object();

        assertThrows(NullPointerException.class, () -> SagaResponse.builder().build());
        assertThrows(NullPointerException.class, () -> SagaResponse.builder()
                .idempotenceId(idempotenceId)
                .build());
        assertThrows(NullPointerException.class, () -> SagaResponse.builder()
                .content(SagaContent.of(content))
                .build());

        var sagaResponse = SagaResponse.builder()
                .idempotenceId(idempotenceId)
                .content(SagaContent.of(content))
                .build();
        assertEquals(idempotenceId, sagaResponse.idempotenceId());
        assertNotNull(sagaResponse.headers());
        assertEquals(content, sagaResponse.content().getContent());
    }
}
