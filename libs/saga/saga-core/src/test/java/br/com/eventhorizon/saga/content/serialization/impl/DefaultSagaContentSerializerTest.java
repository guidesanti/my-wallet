package br.com.eventhorizon.saga.content.serialization.impl;

import br.com.eventhorizon.common.serialization.OffsetDateTimeJsonDeserializer;
import br.com.eventhorizon.common.serialization.OffsetDateTimeJsonSerializer;
import br.com.eventhorizon.saga.proto.TestProto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultSagaContentSerializerTest {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class SomeSerializableClass implements Serializable {

        @Serial
        private static final long serialVersionUID = 4466001458136940384L;

        private String field1;

        private String field2;

        private String field3;

        private OffsetDateTime offsetDateTime;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class SomeClass {

        private String field1;

        private String field2;

        private String field3;

        private OffsetDateTime offsetDateTime;
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
        var serializer = new DefaultSagaContentSerializer();

        // Serialize
        byte[] serializedContent = serializer.serialize(content);
        Assertions.assertArrayEquals(content.toByteArray(), serializedContent);

        // Deserialize
        var deserializedSagaContent = serializer.deserialize(serializedContent, TestProto.TestMessage.class);
        assertEquals(TestProto.TestMessage.parseFrom(serializedContent), deserializedSagaContent);
        assertEquals(content, deserializedSagaContent);
    }

    @Test
    public void testSerializableSerializeDeserialize() throws IOException {
        var offsetDateTime = OffsetDateTime.now();
        var content = new SomeSerializableClass("field1", "field2", "field3", offsetDateTime);
        var serializer = new DefaultSagaContentSerializer();

        // Serialize
        byte[] serializedContent = serializer.serialize(content);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(content);
        assertArrayEquals(serializedContent, byteArrayOutputStream.toByteArray());

        // Deserialize
        var deserializedSagaContent = serializer.deserialize(serializedContent, SomeSerializableClass.class);
        assertEquals(content, deserializedSagaContent);
    }

    @Test
    public void testUnknownSerializeDeserialize() throws JsonProcessingException {
        var simpleModule = new SimpleModule()
                .addSerializer(OffsetDateTime.class, new OffsetDateTimeJsonSerializer())
                .addDeserializer(OffsetDateTime.class, new OffsetDateTimeJsonDeserializer());
        var objectMapper = new ObjectMapper().registerModule(simpleModule);

        var offsetDateTime = OffsetDateTime.now();
        var content = new SomeClass("field1", "field2", "field3", offsetDateTime);
        var serializer = new DefaultSagaContentSerializer();

        // Serialize
        byte[] serializedContent = serializer.serialize(content);
        assertArrayEquals(objectMapper.writeValueAsString(content).getBytes(), serializedContent);

        // Deserialize
        var deserializedSagaContent = serializer.deserialize(serializedContent, SomeClass.class);
        assertEquals(content, deserializedSagaContent);
    }
}
