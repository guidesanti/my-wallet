package br.com.eventhorizon.mywallet.ms.kafka.consumer.infrastructure.messaging.mapper;

import br.com.eventhorizon.mywallet.ms.kafka.consumer.domain.domain.Message;
import br.com.eventhorizon.mywallet.proto.TestMessageProto;
import com.google.protobuf.ByteString;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface ProtoMapper {

    ProtoMapper INSTANCE = Mappers.getMapper(ProtoMapper.class);

    default byte[] mapStringToByteArray(String string) {
        return string.getBytes();
    }

    @Mapping(target = "content",    source = "content")
    Message map(TestMessageProto proto);

    default byte[] map(ByteString byteString) {
        return byteString.toByteArray();
    }
}
