package br.com.eventhorizon.saga.content.serialization.impl;

import br.com.eventhorizon.saga.content.SagaContent;
import br.com.eventhorizon.saga.proto.TestProto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultSagaContentSerializerTest {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class SomeClass {

        private String field1;

        private String field2;

        private String field3;
    }

    @Test
    public void testSerializeOfNull() {
        assertThrows(IllegalArgumentException.class, () -> new DefaultSagaContentSerializer().serialize(null));
    }

    @Test
    public void testDeserializeOfNull() {
        assertThrows(IllegalArgumentException.class, () -> new DefaultSagaContentSerializer().deserialize(null, Object.class));
        assertThrows(IllegalArgumentException.class, () -> new DefaultSagaContentSerializer().deserialize(new byte[0], null));
    }

    @Test
    public void testProtobufSerializeDeserialize() throws InvalidProtocolBufferException {
        var content = TestProto.TestMessage.newBuilder()
                .setField1("value1")
                .setField2("value2")
                .setField3("value3")
                .build();
        var sagaContent = SagaContent.of(content);
        var serializer = new DefaultSagaContentSerializer();

        // Serialize
        byte[] serializedContent = serializer.serialize(sagaContent);
        Assertions.assertArrayEquals(content.toByteArray(), serializedContent);

        // Deserialize
        var deserializedSagaContent = serializer.deserialize(serializedContent, TestProto.TestMessage.class);
        assertEquals(TestProto.TestMessage.parseFrom(serializedContent), deserializedSagaContent.getContent());
        assertEquals(content, deserializedSagaContent.getContent());
    }

    @Test
    public void testUnknownSerializeDeserialize() throws JsonProcessingException {
        var content = new SomeClass("field1", "field2", "field3");
        var sagaContent = SagaContent.of(content);
        var serializer = new DefaultSagaContentSerializer();

        // Serialize
        byte[] serializedContent = serializer.serialize(sagaContent);
        assertArrayEquals(new ObjectMapper().writeValueAsString(content).getBytes(), serializedContent);

        // Deserialize
        var deserializedSagaContent = serializer.deserialize(serializedContent, SomeClass.class);
        assertEquals(content, deserializedSagaContent.getContent());
    }
}
