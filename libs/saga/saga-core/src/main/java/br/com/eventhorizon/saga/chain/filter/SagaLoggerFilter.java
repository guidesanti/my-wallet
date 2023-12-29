package br.com.eventhorizon.saga.chain.filter;

import br.com.eventhorizon.saga.SagaMessage;
import br.com.eventhorizon.saga.SagaOutput;
import br.com.eventhorizon.saga.chain.SagaChain;
import br.com.eventhorizon.saga.chain.SagaPhase;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SagaLoggerFilter implements SagaFilter {

    @Override
    public int order() {
        return SagaPhase.START.before();
    }

    @Override
    public SagaOutput filter(List<SagaMessage> messages, SagaChain chain) throws Exception {
        try {
            log.info("##### BEGIN LOCAL SAGA TRANSACTION #####");
            return chain.next(messages);
        } finally {
            log.info("##### END LOCAL SAGA TRANSACTION #####");
        }
    }
}