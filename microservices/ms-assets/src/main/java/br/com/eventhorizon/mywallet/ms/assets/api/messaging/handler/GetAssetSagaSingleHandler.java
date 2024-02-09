package br.com.eventhorizon.mywallet.ms.assets.api.messaging.handler;

import br.com.eventhorizon.mywallet.common.proto.AssetsProto;
import br.com.eventhorizon.mywallet.ms.assets.business.model.Asset;
import br.com.eventhorizon.saga.SagaMessage;
import br.com.eventhorizon.saga.SagaOutput;
import br.com.eventhorizon.saga.handler.SagaSingleHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GetAssetSagaSingleHandler implements SagaSingleHandler<Asset, byte[]> {

    @Override
    public SagaOutput<Asset> handle(SagaMessage<byte[]> message) throws Exception {
        log.info("Handling message {}", message);
        log.info("Handling parsed message {}", AssetsProto.GetAssetCommandRequest.parseFrom(message.content()));
        return null;
    }
}
