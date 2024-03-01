package br.com.eventhorizon.common.validation.predicate;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringPredicateTest {

    @Test
    void testStringIsAlpha() {
        assertFalse(StringPredicate.stringIsAlpha().test(null));
        assertFalse(StringPredicate.stringIsAlpha().test(""));
        assertFalse(StringPredicate.stringIsAlpha().test(" "));
        for (int i = 0; i < 1000; i++) {
            assertFalse(StringPredicate.stringIsAlpha().test(RandomStringUtils.randomNumeric(1, 100)));
        }
        for (int i = 0; i < 1000; i++) {
            assertTrue(StringPredicate.stringIsAlpha().test(RandomStringUtils.randomAlphabetic(1, 100)));
        }

        assertFalse(StringPredicate.stringIsAlpha(null).test(null));
        assertFalse(StringPredicate.stringIsAlpha(o -> null).test(""));
        assertFalse(StringPredicate.stringIsAlpha(o -> null).test(" "));
    }
}
