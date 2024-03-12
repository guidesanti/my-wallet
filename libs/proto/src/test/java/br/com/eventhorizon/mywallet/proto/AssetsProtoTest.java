package br.com.eventhorizon.mywallet.proto;

import br.com.eventhorizon.common.utils.HexUtils;
import br.com.eventhorizon.mywallet.common.proto.AssetsProto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class AssetsProtoTest {

    @Test
    public void testGetAsset() {
        var obj = AssetsProto.GetAssetCommandRequest.newBuilder()
                .setAssetId("0d5efd82-2640-4453-b4dc-0c49cbafed85")
                .build();
        log.info("Object: {}", obj);
        var serialized = obj.toByteArray();
        var serializedHexString = HexUtils.byteArrayToHexString(serialized);
        log.info("Serialized (HEX): {}", serializedHexString);
    }

//    @Test
//    public void deserializeHexString() throws InvalidProtocolBufferException {
//        var serializedHexString = "0A700A2464313466333533382D323536632D346337372D386636322D393166316166353330643437121E323032332D31322D30355431373A32323A33392E3233333734333230315A28033203312E303A03312E3042004A1664657374696E6174696F6E2D6163636F756E742D696452005A00";
//        System.out.println("Serialized (HEX): " + serializedHexString);
//        var serialized = HexUtils.hexStringToByteArray(serializedHexString);
//        var obj = TransactionsProto.TransactionCreatedEvent.parseFrom(serialized);
//        System.out.println("Object: ");
//        System.out.println(obj);
//    }
//
//    @Test
//    public void deserializeBase64String() throws InvalidProtocolBufferException {
//        var serializedBase64String = "22436E414B4A4463335A6A49774D446B354C5755344D4455744E4441784E7930344F445A694C575A684D325A6C4D7A673559545132595249654D6A41794D7930784D6930774E5651784E7A6F784D6A6F794F5334784E6A51304E5467314D546C614B414D79417A45754D446F444D5334775167424B466D526C63335270626D4630615739754C57466A59323931626E51746157525341466F4122";
//        System.out.println("Serialized (Base64): " + serializedBase64String);
//        var serializedHexString = HexUtils.base64StringToHexString(serializedBase64String);
//        System.out.println("Serialized (HEX): " + serializedHexString);
//        var serialized = HexUtils.hexStringToByteArray(serializedHexString);
//        var obj = TransactionsProto.TransactionCreatedEvent.parseFrom(serialized);
//        System.out.println("Object: ");
//        System.out.println(obj);
//    }
}
