package br.com.eventhorizon.mywallet.ms.kafka.producer.infrastructure.messaging.mapper;

import br.com.eventhorizon.mywallet.ms.kafka.producer.domain.domain.Message;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProtoMapperTest {

    @Test
    void test() {
        // Given
        var message = Message.builder()
                .batchId(UUID.randomUUID())
                .createdAt(Instant.now())
                .sequence(10)
                .content("test".getBytes())
                .build();

        // When
        var proto = ProtoMapper.INSTANCE.mapToProto(message);

        // Then
        assertEquals("test", proto.getContent());
    }
}
