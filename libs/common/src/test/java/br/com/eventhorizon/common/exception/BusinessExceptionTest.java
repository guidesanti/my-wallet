package br.com.eventhorizon.common.exception;

import br.com.eventhorizon.common.refusal.Refusal;
import br.com.eventhorizon.common.refusal.RefusalReasonCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class BusinessExceptionTest {

    @Test
    public void testConstructorWithErrorOnly() {
        Refusal refusal = Refusal.of(RefusalReasonCode.app("DOMAIN", "CODE"), "Message");
        BusinessException exception = new BusinessException(refusal);
        assertEquals(refusal, exception.getRefusal());
    }

    @Test
    public void testConstructorWithErrorAndThrowable() {
        Refusal refusal = Refusal.of(RefusalReasonCode.app("DOMAIN", "CODE"), "Message");
        Throwable cause = new RuntimeException("Cause");
        BusinessException exception = new BusinessException(refusal, cause);
        assertEquals(refusal, exception.getRefusal());
        assertEquals(cause, exception.getCause());
    }

    @Test
    public void testConstructorWithNullError() {
        assertThrows(NullPointerException.class, () -> new BusinessException(null));
        Throwable cause = new RuntimeException("Cause");
        assertThrows(NullPointerException.class, () -> new BusinessException(null, cause));
    }

    @Test
    void testLog() {
        var failureException = new FailureException("");
        log.error("REFUSED: {}", failureException.getMessage(), failureException);
        log.error("REFUSED", failureException);
    }
}
