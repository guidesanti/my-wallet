package br.com.eventhorizon.saga.content.checker;

import br.com.eventhorizon.common.serialization.OffsetDateTimeJsonSerializer;
import br.com.eventhorizon.common.utils.HexUtils;
import br.com.eventhorizon.saga.SagaMessage;
import br.com.eventhorizon.saga.proto.TestProto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.protobuf.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MD5SumSagaContentCheckerTest {

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
    void testChecksumOfNull() {
        // Given
        var checker = new MD5SumSagaContentChecker<>();

        // Then
        assertThrows(IllegalArgumentException.class, () -> checker.checksum(null));
        assertThrows(IllegalArgumentException.class, () -> checker.checksum(SagaMessage.builder().build()));
    }

    @Test
    void testProtobufChecksum() throws NoSuchAlgorithmException {
        // Given
        var content = TestProto.TestMessage.newBuilder()
                .setField1("value1")
                .setField2("value2")
                .setField3("value3")
                .build();
        var checker = new MD5SumSagaContentChecker<>();

        // When
        var checksum = checker.checksum(SagaMessage.builder().content(content).build());

        // Then
        assertEquals(checksum(content.toByteArray()), checksum);
    }

    @Test
    void testSerializableChecksum() throws NoSuchAlgorithmException, IOException {
        // Given
        var offsetDateTime = OffsetDateTime.now();
        var content = new SomeSerializableClass("field1", "field2", "field3", offsetDateTime);
        var checker = new MD5SumSagaContentChecker<>();

        // When
        var checksum = checker.checksum(SagaMessage.builder().content(content).build());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(content);

        // Then
        assertEquals(checksum(byteArrayOutputStream.toByteArray()), checksum);
    }

    @Test
    void testUnknownChecksum() throws NoSuchAlgorithmException, IOException {
        // Given
        var objectMapper = new ObjectMapper().registerModule(
                new SimpleModule().addSerializer(OffsetDateTime.class, new OffsetDateTimeJsonSerializer()));
        var offsetDateTime = OffsetDateTime.now();
        var content = new SomeClass("field1", "field2", "field3", offsetDateTime);
        var checker = new MD5SumSagaContentChecker<>();

        // When
        var checksum = checker.checksum(SagaMessage.builder().content(content).build());

        // Then
        assertEquals(checksum(objectMapper.writeValueAsString(content).getBytes()), checksum);
    }

    @Test
    void testChecksumOnException() throws NoSuchAlgorithmException {
        // Given
        var content = mock(Message.class);
        doThrow(RuntimeException.class).when(content).toByteArray();
        var checker = new MD5SumSagaContentChecker<>();

        // When
        var checksum = checker.checksum(SagaMessage.builder().content(content).build());

        // Then
        assertEquals(checksum(String.valueOf(content.hashCode()).getBytes()), checksum);
    }

    private String checksum(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(bytes);
        return HexUtils.byteArrayToHexString(messageDigest.digest());
    }
}
