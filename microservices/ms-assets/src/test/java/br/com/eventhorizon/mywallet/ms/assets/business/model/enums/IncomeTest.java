package br.com.eventhorizon.mywallet.ms.assets.business.model.enums;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.mongodb.assertions.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IncomeTypeTest {

    private static final Map<String, IncomeType> INCOME_TYPES = new HashMap<>();

    static {
        INCOME_TYPES.put("NONE", IncomeType.NONE);
        INCOME_TYPES.put("VARIABLE", IncomeType.VARIABLE);
        INCOME_TYPES.put("FIXED", IncomeType.FIXED);
        INCOME_TYPES.put("INDEXED", IncomeType.INDEXED);
        INCOME_TYPES.put("MIXED", IncomeType.MIXED);
        INCOME_TYPES.put("OTHER", IncomeType.OTHER);
        INCOME_TYPES.put("UNKNOWN", IncomeType.UNKNOWN);
    }

    @Test
    void testCount() {
        // This is to ensure new values will be added to the tests
        assertEquals(INCOME_TYPES.size(), IncomeType.values().length);
    }

    @Test
    void testOf() {
        INCOME_TYPES.forEach((name, incomeType) -> assertEquals(incomeType, IncomeType.of(name)));

        for (int i = 0; i < 1000; i++) {
            assertEquals(IncomeType.UNKNOWN, IncomeType.of(RandomStringUtils.randomAlphanumeric(1, 256)));
        }
    }

    @Test
    void testGetDescription() {
        INCOME_TYPES.forEach((name, incomeType) -> {
            assertFalse(StringUtils.isBlank(incomeType.getDescription()));
        });
    }
}
