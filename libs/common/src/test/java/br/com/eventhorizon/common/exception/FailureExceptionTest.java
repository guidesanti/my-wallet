package br.com.eventhorizon.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class FailureExceptionTest {

    @Test
    void testLog() {
        var failureException = new FailureException("Message");
        log.error("FAILURE: {}", failureException.getMessage(), failureException);
        log.error("FAILURE", failureException);
    }
}
