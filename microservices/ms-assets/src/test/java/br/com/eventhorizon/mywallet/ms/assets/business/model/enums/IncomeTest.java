package br.com.eventhorizon.mywallet.ms.assets.business.model.enums;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.mongodb.assertions.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IncomeTest {

    private static final Map<String, Income> INCOME_TYPES = new HashMap<>();

    static {
        INCOME_TYPES.put("NONE", Income.NONE);
        INCOME_TYPES.put("VARIABLE", Income.VARIABLE);
        INCOME_TYPES.put("FIXED", Income.FIXED);
        INCOME_TYPES.put("INDEXED", Income.INDEXED);
        INCOME_TYPES.put("MIXED", Income.MIXED);
        INCOME_TYPES.put("OTHER", Income.OTHER);
        INCOME_TYPES.put("UNKNOWN", Income.UNKNOWN);
    }

    @Test
    void testCount() {
        // This is to ensure new values will be added to the tests
        assertEquals(INCOME_TYPES.size(), Income.values().length);
    }

    @Test
    void testOf() {
        INCOME_TYPES.forEach((name, income) -> assertEquals(income, Income.of(name)));

        for (int i = 0; i < 1000; i++) {
            assertEquals(Income.UNKNOWN, Income.of(RandomStringUtils.randomAlphanumeric(1, 256)));
        }
    }

    @Test
    void testGetDescription() {
        INCOME_TYPES.forEach((name, income) -> {
            assertFalse(StringUtils.isBlank(income.getDescription()));
        });
    }
}
