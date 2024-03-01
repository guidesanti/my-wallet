package br.com.eventhorizon.common.validation.predicate;

import org.junit.jupiter.api.Test;

import static br.com.eventhorizon.common.validation.predicate.LogicalPredicate.isFalse;
import static br.com.eventhorizon.common.validation.predicate.LogicalPredicate.isTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LogicalPredicateTest {

    @Test
    void testIsFalse() {
        assertFalse(isFalse().test(null));
        assertFalse(isFalse().test(true));
        assertTrue(isFalse().test(false));
        assertFalse(isFalse().test(Boolean.TRUE));
        assertTrue(isFalse().test(Boolean.FALSE));

        assertFalse(isFalse(o -> null).test(null));
        assertFalse(isFalse(o -> null).test(new Object()));
        assertFalse(isFalse(o -> false).test(null));
        assertTrue(isFalse(o -> false).test(new Object()));
        assertFalse(isFalse(o -> true).test(null));
        assertFalse(isFalse(o -> true).test(new Object()));
    }

    @Test
    void testIsTrue() {
        assertFalse(isTrue().test(null));
        assertTrue(isTrue().test(true));
        assertFalse(isTrue().test(false));
        assertTrue(isTrue().test(Boolean.TRUE));
        assertFalse(isTrue().test(Boolean.FALSE));

        assertFalse(isTrue(o -> null).test(null));
        assertFalse(isTrue(o -> null).test(new Object()));
        assertFalse(isTrue(o -> false).test(null));
        assertFalse(isTrue(o -> false).test(new Object()));
        assertFalse(isTrue(o -> true).test(null));
        assertTrue(isTrue(o -> true).test(new Object()));
    }
}
