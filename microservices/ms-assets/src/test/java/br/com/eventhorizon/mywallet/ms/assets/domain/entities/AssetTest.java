package br.com.eventhorizon.mywallet.ms.assets.domain.entities;

import br.com.fluentvalidator.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssetTest {

    @Test
    void testCanCreate() {
        // When
        var result = Asset.canCreate(null, "short-name", "long-name", null, "description", null);

        // Then
        assertFalse(result.isValid());
        assertEquals(1, result.getErrors().size());
    }

    @Test
    void testCreate() {
        // When
        ValidationException exception = null;
        try {
            Asset.create(null, "short-name", "long-name", null, "description", null);
        } catch (ValidationException ex) {
            exception = ex;
        }

        // Then
        assertNotNull(exception);
    }
}
