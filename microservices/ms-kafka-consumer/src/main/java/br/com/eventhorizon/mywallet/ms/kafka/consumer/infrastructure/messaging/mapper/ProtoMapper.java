package br.com.eventhorizon.mywallet.ms.kafka.consumer.infrastructure.messaging.mapper;

import br.com.eventhorizon.mywallet.ms.kafka.producer.domain.domain.Message;
import br.com.eventhorizon.mywallet.proto.TestMessageProto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface ProtoMapper {

    ProtoMapper INSTANCE = Mappers.getMapper(ProtoMapper.class);

    default String mapByteArrayToString(byte[] bytes) {
        return new String(bytes);
    }

    @Mapping(target = "batchId", source = "batchId")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "sequence", source = "sequence")
    @Mapping(target = "content", source = "content")
    TestMessageProto mapToProto(Message message);
}
