package br.com.eventhorizon.mywallet.proto;

import br.com.eventhorizon.common.utils.HexUtils;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class TestMessageProtoTest {

    private static final String STRING = "Hello World!";

    @Test
    public void serializeToHexString() {
        var obj = TestMessageProto.newBuilder()
                .setContent(ByteString.copyFromUtf8(STRING))
                .build();
        log.info("Object: {}", obj);
        var serialized = obj.toByteArray();
        var serializedHexString = HexUtils.byteArrayToHexString(serialized);
        log.info("Serialized (HEX): {}", serializedHexString);
    }

    @Test
    public void deserializeFromHexString() throws InvalidProtocolBufferException {
        var serializedHexString = "0A0C48656C6C6F20576F726C6421";
        System.out.println("Serialized (HEX): " + serializedHexString);
        var serialized = HexUtils.hexStringToByteArray(serializedHexString);
        var obj = TestMessageProto.parseFrom(serialized);
        System.out.println("Object: ");
        System.out.println(obj);
        assertEquals(ByteString.copyFromUtf8(STRING), obj.getContent());
    }
}
