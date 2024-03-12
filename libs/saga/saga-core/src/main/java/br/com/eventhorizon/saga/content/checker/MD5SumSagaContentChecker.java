package br.com.eventhorizon.saga.content.checker;

import br.com.eventhorizon.common.utils.HexUtils;
import br.com.eventhorizon.saga.SagaMessage;
import br.com.eventhorizon.common.serialization.OffsetDateTimeJsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.protobuf.Message;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.time.OffsetDateTime;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

public class MD5SumSagaContentChecker<M> implements SagaContentChecker<M> {

    private final ObjectMapper objectMapper;

    public MD5SumSagaContentChecker() {
        this.objectMapper = new ObjectMapper()
                .registerModule(new SimpleModule().addSerializer(OffsetDateTime.class, new OffsetDateTimeJsonSerializer()));
    }

    @Override
    public String checksum(SagaMessage<M> message) {
        assertNotNull(message);
        assertNotNull(message.content());
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(toByteArray(message.content()));
            return HexUtils.byteArrayToHexString(messageDigest.digest());
        } catch (Exception ex) {
            throw new SagaCheckerException("Failed to calculate SAGA checksum for message of type '" + message.content().getClass().getName() + "'", ex);
        }
    }

    private byte[] toByteArray(M content) {
        try {
            byte[] contentByteArray;

            if (content instanceof Message) {
                contentByteArray = ((Message) content).toByteArray();
            } else if (content instanceof Serializable) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(content);
                contentByteArray = byteArrayOutputStream.toByteArray();
            } else {
                contentByteArray = objectMapper.writeValueAsString(content).getBytes();
            }

            return contentByteArray;
        } catch (Exception ex) {
            return String.valueOf(content.hashCode()).getBytes();
        }
    }
}
