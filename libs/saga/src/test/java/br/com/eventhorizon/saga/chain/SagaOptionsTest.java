package br.com.eventhorizon.saga.chain;

import br.com.eventhorizon.saga.SagaOption;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SagaOptionsTest {

    @Test
    public void testBuild() {
        // Null value, should return all default values
        assertDefaultValues(SagaOptions.of(null));

        // Empty map, should return all default values
        assertDefaultValues(new SagaOptions());
        assertDefaultValues(new SagaOptions(null));
        assertDefaultValues(SagaOptions.of(Collections.emptyMap()));

        // Custom options
        var options = SagaOptions.of(Map.of(
                SagaOption.EVENT_REPUBLISH_ENABLED, false
        ));
        assertEquals(false, options.get(SagaOption.EVENT_REPUBLISH_ENABLED));
        assertEquals(Long.valueOf(86400L), options.get(SagaOption.TRANSACTION_TTL));

        options = SagaOptions.of(Map.of(
                SagaOption.TRANSACTION_TTL, 10000L
        ));
        assertEquals(true, options.get(SagaOption.EVENT_REPUBLISH_ENABLED));
        assertEquals(Long.valueOf(10000L), options.get(SagaOption.TRANSACTION_TTL));

        options = new SagaOptions()
                .option(SagaOption.EVENT_REPUBLISH_ENABLED, false)
                .option(SagaOption.TRANSACTION_TTL, 250L);
        assertEquals(false, options.get(SagaOption.EVENT_REPUBLISH_ENABLED));
        assertEquals(Long.valueOf(250L), options.get(SagaOption.TRANSACTION_TTL));
    }

    public void assertDefaultValues(SagaOptions options) {
        assertEquals(SagaOption.EVENT_REPUBLISH_ENABLED.getDefaultValue(), options.get(SagaOption.EVENT_REPUBLISH_ENABLED));
        assertEquals(SagaOption.TRANSACTION_TTL.getDefaultValue(), options.get(SagaOption.TRANSACTION_TTL));
    }
}
