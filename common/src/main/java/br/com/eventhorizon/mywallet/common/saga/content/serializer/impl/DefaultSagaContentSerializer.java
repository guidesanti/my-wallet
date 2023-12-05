package br.com.eventhorizon.mywallet.common.saga.content.serializer.impl;

import br.com.eventhorizon.mywallet.common.saga.content.SagaContent;
import br.com.eventhorizon.mywallet.common.saga.content.serializer.SagaContentSerializer;
import br.com.eventhorizon.mywallet.common.serialization.OffsetDateTimeJsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.protobuf.Message;
import lombok.Getter;

import java.lang.reflect.Method;
import java.time.OffsetDateTime;

@Getter
public class DefaultSagaContentSerializer implements SagaContentSerializer {

    private final Class<?> targetClass;

    private final ObjectMapper objectMapper;

    public DefaultSagaContentSerializer(Class<?> targetClass) {
        this.targetClass = targetClass;
        this.objectMapper = new ObjectMapper()
                .registerModule(new SimpleModule().addSerializer(OffsetDateTime.class, new OffsetDateTimeJsonSerializer()));
    }

    @Override
    public byte[] serialize(SagaContent content) {
        try {
            if (content.getContent() instanceof Message) {
                return ((Message) content.getContent()).toByteArray();
            } else {
                return objectMapper.writeValueAsString(content.getContent()).getBytes();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize SAGA content object of class " + targetClass.getName(), e);
        }
    }

    @Override
    public SagaContent deserialize(byte[] content) {
        try {
            if (Message.class.isAssignableFrom(targetClass)) {
                var array = new byte[0];
                Method method = targetClass.getMethod("parseFrom", array.getClass());
                return SagaContent.of(method.invoke(null, content));
            } else {
                return SagaContent.of(objectMapper.readValue(content, targetClass));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize SAGA content object of class " + targetClass.getName(), e);
        }
    }
}
