package br.com.eventhorizon.saga.content.serialization.impl;

import br.com.eventhorizon.saga.content.serialization.exception.SagaDeserializationException;
import br.com.eventhorizon.saga.content.serialization.exception.SagaSerializationException;
import br.com.eventhorizon.saga.content.serialization.SagaContentSerializer;
import br.com.eventhorizon.common.serialization.OffsetDateTimeJsonSerializer;
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
    public <T> byte[] serialize(T content) {
        assertNotNull(content);
        try {
            if (content instanceof Message) {
                return ((Message) content).toByteArray();
            } else {
                return objectMapper.writeValueAsString(content).getBytes();
            }
        } catch (Exception e) {
            throw new SagaSerializationException("Failed to serialize SAGA content object of type '" + content.getClass() + "'", e);
        }
    }

    @Override
    public <T> T deserialize(byte[] content, Class<T> type) {
        assertNotNull(content);
        assertNotNull(type);
        try {
            if (Message.class.isAssignableFrom(type)) {
                var array = new byte[0];
                Method method = type.getMethod("parseFrom", array.getClass());
                return (T) method.invoke(null, content);
            } else {
                return objectMapper.readValue(content, type);
            }
        } catch (Exception e) {
            throw new SagaDeserializationException("Failed to deserialize SAGA content object of type '" + type.getName() + "'", e);
        }
    }
}
