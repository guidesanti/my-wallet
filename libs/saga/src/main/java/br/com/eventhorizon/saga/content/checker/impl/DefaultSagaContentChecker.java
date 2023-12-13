package br.com.eventhorizon.saga.content.checker.impl;

import br.com.eventhorizon.common.util.HexUtils;
import br.com.eventhorizon.saga.SagaMessage;
import br.com.eventhorizon.saga.content.checker.SagaContentChecker;
import br.com.eventhorizon.common.serialization.OffsetDateTimeJsonSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.protobuf.Message;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;

public class DefaultSagaContentChecker implements SagaContentChecker {

    private final ObjectMapper objectMapper;

    public DefaultSagaContentChecker() {
        this.objectMapper = new ObjectMapper()
                .registerModule(new SimpleModule().addSerializer(OffsetDateTime.class, new OffsetDateTimeJsonSerializer()));
    }

    @Override
    public String checksum(SagaMessage message) {
        try {
            var content = message.content().getContent();
            byte[] contentByteArray;

            if (content instanceof Message) {
                contentByteArray = ((Message) content).toByteArray();
            } else {
                contentByteArray = objectMapper.writeValueAsString(content).getBytes();
            }

            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(contentByteArray);
            return HexUtils.byteArrayToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
