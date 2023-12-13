package br.com.eventhorizon.saga;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SagaIdempotenceIdTest {

    @Test
    public void testConstructor() {
//        var operationId = new DefaultSagaOperationId("", "", 0); // This should not compile

        // Invalid date
        assertThrows(InvalidSagaIdempotenceIdException.class, () -> SagaIdempotenceId.of("", "0000000000", "111222333444555666", 0));
        assertThrows(InvalidSagaIdempotenceIdException.class, () -> SagaIdempotenceId.of("2023010", "0000000000", "111222333444555666", 0));
        assertThrows(InvalidSagaIdempotenceIdException.class, () -> SagaIdempotenceId.of("20230101", "000000000", "111222333444555666", 0));

        // Invalid client ID
        assertThrows(InvalidSagaIdempotenceIdException.class, () -> SagaIdempotenceId.of("20230101", "000000000", "111222333444555666", 0));
        assertThrows(InvalidSagaIdempotenceIdException.class, () -> SagaIdempotenceId.of("20230101", "00000000000", "111222333444555666", 0));
        assertThrows(InvalidSagaIdempotenceIdException.class, () -> SagaIdempotenceId.of("20230101", "000000000?", "111222333444555666", 0));

        // Invalid sequence number
        assertThrows(InvalidSagaIdempotenceIdException.class, () -> SagaIdempotenceId.of("20230101", "0123456789", "11122233344455566", 0));
        assertThrows(InvalidSagaIdempotenceIdException.class, () -> SagaIdempotenceId.of("20230101", "0123456789", "1112223334445556666", 0));
        assertThrows(InvalidSagaIdempotenceIdException.class, () -> SagaIdempotenceId.of("20230101", "0123456789", "11122233344455566a", 0));
        assertThrows(InvalidSagaIdempotenceIdException.class, () -> SagaIdempotenceId.of("20230101", "0123456789", "11122233344455566A", 0));

        SagaIdempotenceId.of("20230101", "abcd1234AB", "111222333444555666", 0);
        SagaIdempotenceId.of("20230101abcd1234AB111222333444555666000");
        SagaIdempotenceId.of("20231020", "1234567890", "111222333444555666", 777);
        SagaIdempotenceId.of("202310201234567890111222333444555666777");
    }

    @Test
    public void testToString() {
        var sagaOperationId = SagaIdempotenceId.of("20230101", "abcd1234AB", "111222333444555666", 0);
        assertEquals("20230101abcd1234AB111222333444555666000", sagaOperationId.toString());

        sagaOperationId = SagaIdempotenceId.of("20230101abcd1234AB111222333444555666000");
        assertEquals("20230101abcd1234AB111222333444555666000", sagaOperationId.toString());
    }
}
