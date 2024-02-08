package br.com.eventhorizon.common.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OffsetDateTimeSerializerDeserializerTest {

    @Test
    void testSerializeDeserialize() throws JsonProcessingException {
        // Given
        var offsetDateTime = OffsetDateTime.now();
        var module = new SimpleModule()
                .addSerializer(OffsetDateTime.class, new OffsetDateTimeJsonSerializer())
                .addDeserializer(OffsetDateTime.class, new OffsetDateTimeJsonDeserializer());
        var objectMapper = new ObjectMapper().registerModule(module);

        // When
        var serialized = objectMapper.writeValueAsString(offsetDateTime);
        var deserialized = objectMapper.readValue(serialized, OffsetDateTime.class);

        // Then
        assertEquals(offsetDateTime, deserialized);
    }
}
