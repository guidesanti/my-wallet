package br.com.eventhorizon.mywallet.ms.kafka.producer.infrastructure.http.mapper;

import br.com.eventhorizon.mywallet.ms.kafka.producer.api.http.model.ProducerRequestDTO;
import br.com.eventhorizon.mywallet.ms.kafka.producer.domain.input.BatchParamsInput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface DtoMapper {

    DtoMapper INSTANCE = Mappers.getMapper(DtoMapper.class);

    @Mapping(target = "batchName", source = "batchName")
    @Mapping(target = "topic", source = "destinationTopic")
    @Mapping(target = "period", source = "period")
    @Mapping(target = "messageSize", source = "messageSize")
    BatchParamsInput mapToInput(ProducerRequestDTO dto);
}
