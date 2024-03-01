package br.com.eventhorizon.common.validation.predicate;

import org.junit.jupiter.api.Test;

import static br.com.eventhorizon.common.validation.predicate.ObjectPredicate.nullValue;
import static br.com.eventhorizon.common.validation.predicate.ObjectPredicate.objectInstanceOf;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ObjectPredicateTest {

    @Test
    void testInstanceOf() {
        assertFalse(objectInstanceOf(Object.class).test(null));
        assertFalse(objectInstanceOf(Boolean.class).test(""));
        assertTrue(objectInstanceOf(Object.class).test(""));
        assertTrue(objectInstanceOf(Object.class).test(new Object()));

        assertFalse(objectInstanceOf(null, null).test(null));
        assertFalse(objectInstanceOf(null, null).test(new Object()));
        assertFalse(objectInstanceOf(null, Object.class).test(new Object()));
        assertTrue(objectInstanceOf(o -> new Object(), Object.class).test(new Object()));
    }

    @Test
    void testNullValue() {
        assertTrue(nullValue().test(null));
        assertFalse(nullValue().test(new Object()));

        assertTrue(nullValue(o -> null).test(null));
        assertTrue(nullValue(o -> null).test(new Object()));
        assertTrue(nullValue(o -> new Object()).test(null));
        assertFalse(nullValue(o -> new Object()).test(new Object()));
    }
}
