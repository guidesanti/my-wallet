package br.com.eventhorizon.saga.chain;

import br.com.eventhorizon.saga.SagaMessage;
import br.com.eventhorizon.saga.chain.filter.SagaFilter;
import br.com.eventhorizon.saga.SagaOutput;
import br.com.eventhorizon.saga.content.checker.SagaContentChecker;
import br.com.eventhorizon.saga.content.serialization.SagaContentSerializer;
import br.com.eventhorizon.saga.handler.SagaBulkHandler;
import br.com.eventhorizon.saga.handler.SagaHandler;
import br.com.eventhorizon.saga.handler.SagaSingleHandler;
import br.com.eventhorizon.saga.messaging.publisher.SagaPublisher;
import br.com.eventhorizon.saga.repository.SagaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class SagaChain<R, M> {

    private final Iterator<SagaFilter<R, M>> filters;

    private final SagaHandler<R, M> handler;

    private final SagaRepository repository;

    private final SagaPublisher publisher;

    private final SagaContentChecker<M> checker;

    private final SagaContentSerializer serializer;

    private final SagaOptions options;

    public SagaRepository repository() {
        return repository;
    }

    public SagaPublisher publisher() {
        return publisher;
    }

    public SagaContentChecker<M> checker() {
        return checker;
    }

    public SagaOptions options() {
        return options;
    }

    public SagaContentSerializer serializer() {
        return serializer;
    }

    public SagaOutput<R> next(List<SagaMessage<M>> messages) throws Exception {
        return doNext(messages);
    }

    private SagaOutput<R> doNext(List<SagaMessage<M>> messages) throws Exception {
        if (filters.hasNext()) {
            return filters.next().filter(messages, this);
        } else if (handler instanceof SagaBulkHandler) {
            log.info("SAGA HANDLING IN BULK MODE");
            return ((SagaBulkHandler<R, M>) handler).handle(messages);
        } else {
            log.info("SAGA HANDLING IN SINGLE MODE");
            var builder = SagaOutput.<R>builder();
            for (var message : messages) {
                var output = ((SagaSingleHandler<R, M>) handler).handle(message);
                if (output != null) {
                    builder.response(output.response()).events(output.events());
                }
            }
            return builder.build();
        }
    }
}
