package br.com.eventhorizon.mywallet.ms.assets.infrastructure.messaging.handler;

import br.com.eventhorizon.messaging.provider.subscriber.SubscriberMessage;
import br.com.eventhorizon.messaging.provider.subscriber.handler.SingleMessageHandler;
import br.com.eventhorizon.mywallet.common.proto.AssetsProto;
import br.com.eventhorizon.mywallet.ms.assets.domain.services.AssetsService;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetAssetSingleMessageHandler implements SingleMessageHandler<byte[]> {

    private final AssetsService assetsService;

    @Override
    public void handle(SubscriberMessage<byte[]> message) {
        try {
            var getAssetCommandRequest = AssetsProto.GetAssetCommandRequest.parseFrom(message.content());
            log.info("Parsed message: {}", getAssetCommandRequest);
//            message.headers().firstValue()
//            assetsService.findAssetById(?, ?, getAssetCommandRequest);
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
    }
}
