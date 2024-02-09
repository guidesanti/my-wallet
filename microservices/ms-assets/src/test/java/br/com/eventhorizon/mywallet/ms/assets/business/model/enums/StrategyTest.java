package br.com.eventhorizon.mywallet.ms.assets.business.model.enums;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.mongodb.assertions.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StrategyTest {

    private static final Map<String, Strategy> STRATEGIES = new HashMap<>();

    static {
        STRATEGIES.put("NO_INCOME", Strategy.NO_INCOME);
        STRATEGIES.put("VARIABLE_INCOME", Strategy.VARIABLE_INCOME);
        STRATEGIES.put("FIXED_INCOME", Strategy.FIXED_INCOME);
        STRATEGIES.put("HYBRID", Strategy.HYBRID);
        STRATEGIES.put("SAVINGS", Strategy.SAVINGS);
        STRATEGIES.put("OTHER", Strategy.OTHER);
        STRATEGIES.put("UNKNOWN", Strategy.UNKNOWN);
    }

    @Test
    void testCount() {
        // This is to ensure new values will be added to the tests
        assertEquals(STRATEGIES.size(), Strategy.values().length);
    }

    @Test
    void testOf() {
        STRATEGIES.forEach((name, strategy) -> assertEquals(strategy, Strategy.of(name)));

        for (int i = 0; i < 1000; i++) {
            assertEquals(Strategy.UNKNOWN, Strategy.of(RandomStringUtils.randomAlphanumeric(1, 256)));
        }
    }

    @Test
    void testGetDescription() {
        STRATEGIES.forEach((name, strategy) -> {
            assertFalse(StringUtils.isBlank(strategy.getDescription()));
        });
    }
}
