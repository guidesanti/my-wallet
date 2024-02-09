package br.com.eventhorizon.saga.chain.filter;

import br.com.eventhorizon.saga.SagaMessage;
import br.com.eventhorizon.saga.SagaOutput;
import br.com.eventhorizon.saga.chain.SagaChain;
import br.com.eventhorizon.saga.chain.SagaPhase;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.List;
import java.util.StringJoiner;

@Slf4j
public class SagaLoggerFilter<R, M> implements SagaFilter<R, M> {

    @Override
    public int order() {
        return SagaPhase.START.before();
    }

    @Override
    public SagaOutput<R> filter(List<SagaMessage<M>> messages, SagaChain<R, M> chain) throws Exception {
        try {
            MDC.put("idempotenceId", buildCompoundIdempotenceId(messages));
            MDC.put("traceId", buildCompoundTraceId(messages));
            log.info("##### BEGIN LOCAL SAGA TRANSACTION #####");
            return chain.next(messages);
        } finally {
            log.info("##### END LOCAL SAGA TRANSACTION #####");
        }
    }

    private String buildCompoundIdempotenceId(List<SagaMessage<M>> messages) {
        var compoundIdempotenceId = new StringJoiner(",");
        messages.forEach(message -> compoundIdempotenceId.add(message.idempotenceId().toString()));
        return compoundIdempotenceId.toString();
    }

    private String buildCompoundTraceId(List<SagaMessage<M>> messages) {
        var compoundTraceId = new StringJoiner(",");
        messages.forEach(message -> compoundTraceId.add(message.traceId()));
        return compoundTraceId.toString();
    }
}
