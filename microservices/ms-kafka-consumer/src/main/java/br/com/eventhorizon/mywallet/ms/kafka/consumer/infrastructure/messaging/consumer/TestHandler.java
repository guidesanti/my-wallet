package br.com.eventhorizon.mywallet.ms.kafka.consumer.infrastructure.messaging.consumer;

import br.com.eventhorizon.messaging.provider.Attribute;
import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMessage;
import br.com.eventhorizon.messaging.provider.subscriber.handler.SingleMessageHandler;
import br.com.eventhorizon.mywallet.ms.kafka.consumer.application.usecase.ConsumeMessage;
import br.com.eventhorizon.mywallet.ms.kafka.consumer.domain.domain.BatchMarker;
import br.com.eventhorizon.mywallet.ms.kafka.consumer.domain.input.ConsumeMessageInput;
import br.com.eventhorizon.mywallet.ms.kafka.consumer.infrastructure.messaging.mapper.ProtoMapper;
import br.com.eventhorizon.mywallet.proto.TestMessageProto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestHandler implements SingleMessageHandler<byte[]> {

    private final ConsumeMessage consumeMessage;

    @Override
    public void handle(SubscriberMessage<byte[]> message) throws Exception {
        var proto = TestMessageProto.parseFrom(message.content());
        var headers = message.headers();
        var attributes = message.attributes();
        var input = new ConsumeMessageInput(
                UUID.fromString(headers.firstValue("batch-id").orElse(BatchMarker.NONE.name())),
                message.headers().firstValue("batch-marker").map(BatchMarker::valueOf).orElse(BatchMarker.NONE),
                ProtoMapper.INSTANCE.map(proto),
                Double.parseDouble(message.attributes().value(Attribute.LATENCY.getName()))
        );
        consumeMessage.call(input);
    }
}
