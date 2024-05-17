package br.com.eventhorizon.common.exception;

import br.com.eventhorizon.common.refusal.Refusal;
import br.com.eventhorizon.common.refusal.RefusalReasonCode;
import static org.junit.jupiter.api.Assertions.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ClientExceptionTest {

    @Test
    public void testConstructorWithErrorOnly() {
        Refusal refusal = Refusal.of(RefusalReasonCode.app("DOMAIN", "CODE"), "Message");
        ClientException exception = new ClientException(refusal);
        assertEquals(refusal, exception.getRefusal());
    }

    @Test
    public void testConstructorWithErrorAndThrowable() {
        Refusal refusal = Refusal.of(RefusalReasonCode.app("DOMAIN", "CODE"), "Message");
        Throwable cause = new RuntimeException("Cause");
        ClientException exception = new ClientException(refusal, cause);
        assertEquals(refusal, exception.getRefusal());
        assertEquals(cause, exception.getCause());
    }

    @Test
    public void testConstructorWithNullError() {
        assertThrows(NullPointerException.class, () -> new ClientException(null));
        Throwable cause = new RuntimeException("Cause");
        assertThrows(NullPointerException.class, () -> new ClientException(null, cause));
    }

    @Test
    void testLog() {
        var failureException = new FailureException("");
        log.error("BAD REQUEST: {}", failureException.getMessage(), failureException);
        log.error("BAD REQUEST", failureException);
    }
}
