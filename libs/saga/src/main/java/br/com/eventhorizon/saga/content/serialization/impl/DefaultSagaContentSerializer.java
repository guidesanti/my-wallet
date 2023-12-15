package br.com.eventhorizon.saga.content.serialization.impl;

import br.com.eventhorizon.saga.content.SagaContent;
import br.com.eventhorizon.saga.content.serialization.SagaContentSerializer;
import br.com.eventhorizon.common.serialization.OffsetDateTimeJsonSerializer;
import br.com.eventhorizon.saga.content.serialization.exception.SagaDeserializationException;
import br.com.eventhorizon.saga.content.serialization.exception.SagaSerializationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.protobuf.Message;
import lombok.Getter;

import java.lang.reflect.Method;
import java.time.OffsetDateTime;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Getter
public class DefaultSagaContentSerializer implements SagaContentSerializer {

    private final ObjectMapper objectMapper;

    public DefaultSagaContentSerializer() {
        this.objectMapper = new ObjectMapper()
                .registerModule(new SimpleModule().addSerializer(OffsetDateTime.class, new OffsetDateTimeJsonSerializer()));
    }

    @Override
    public byte[] serialize(SagaContent content) {
        assertNotNull(content);
        try {
            if (content.getContent() instanceof Message) {
                return ((Message) content.getContent()).toByteArray();
            } else {
                return objectMapper.writeValueAsString(content.getContent()).getBytes();
            }
        } catch (Exception e) {
            throw new SagaSerializationException("Failed to serialize SAGA content object of type '" + content.getContent().getClass() + "'", e);
        }
    }

    @Override
    public <T> SagaContent deserialize(byte[] content, Class<T> type) {
        assertNotNull(content);
        assertNotNull(type);
        try {
            if (Message.class.isAssignableFrom(type)) {
                var array = new byte[0];
                Method method = type.getMethod("parseFrom", array.getClass());
                return SagaContent.of(method.invoke(null, content));
            } else {
                return SagaContent.of(objectMapper.readValue(content, type));
            }
        } catch (Exception e) {
            throw new SagaDeserializationException("Failed to deserialize SAGA content object of type '" + type.getName() + "'", e);
        }
    }
}
