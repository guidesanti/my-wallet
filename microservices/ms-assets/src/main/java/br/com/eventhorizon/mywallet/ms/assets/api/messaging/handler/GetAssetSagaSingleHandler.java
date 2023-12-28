package br.com.eventhorizon.mywallet.ms.assets.api.messaging.handler;

import br.com.eventhorizon.saga.SagaMessage;
import br.com.eventhorizon.saga.SagaOutput;
import br.com.eventhorizon.saga.handler.SagaSingleHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GetAssetSagaSingleHandler implements SagaSingleHandler {

    @Override
    public SagaOutput handle(SagaMessage message) throws Exception {
        log.info("Handling message {}", message);
        return null;
    }
}
