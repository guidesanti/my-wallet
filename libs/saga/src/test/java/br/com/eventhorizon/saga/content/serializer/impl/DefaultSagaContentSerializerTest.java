package br.com.eventhorizon.saga.content.serializer.impl;

import br.com.eventhorizon.mywallet.common.proto.AssetsProto;
import br.com.eventhorizon.saga.content.SagaContent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void testProtobufSerializeDeserialize() throws InvalidProtocolBufferException {
        var content = AssetsProto.Asset.newBuilder()
                .setId("id")
                .setShortName("short-name")
                .setLongName("long-name")
                .setStrategy("strategy")
                .setType("type")
                .build();
        var sagaContent = SagaContent.of(content);
        var serializer = new DefaultSagaContentSerializer(AssetsProto.Asset.class);

        // Serialize
        byte[] serializedContent = serializer.serialize(sagaContent);
        Assertions.assertArrayEquals(content.toByteArray(), serializedContent);

        // Deserialize
        var deserializedSagaContent = serializer.deserialize(serializedContent);
        assertEquals(AssetsProto.Asset.parseFrom(serializedContent), deserializedSagaContent.getContent());
        assertEquals(content, deserializedSagaContent.getContent());
    }

    @Test
    public void testUnknownSerializeDeserialize() throws JsonProcessingException {
        var content = new SomeClass("field1", "field2", "field3");
        var sagaContent = SagaContent.of(content);
        var serializer = new DefaultSagaContentSerializer(SomeClass.class);

        // Serialize
        byte[] serializedContent = serializer.serialize(sagaContent);
        assertArrayEquals(new ObjectMapper().writeValueAsString(content).getBytes(), serializedContent);

        // Deserialize
        var deserializedSagaContent = serializer.deserialize(serializedContent);
        assertEquals(content, deserializedSagaContent.getContent());
    }
}
