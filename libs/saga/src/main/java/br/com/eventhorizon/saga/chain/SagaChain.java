package br.com.eventhorizon.saga.chain;

import br.com.eventhorizon.saga.SagaMessage;
import br.com.eventhorizon.saga.chain.filter.SagaFilter;
import br.com.eventhorizon.saga.SagaOutput;
import br.com.eventhorizon.saga.content.checker.SagaContentChecker;
import br.com.eventhorizon.saga.content.serializer.SagaContentSerializer;
import br.com.eventhorizon.saga.handler.SagaBulkHandler;
import br.com.eventhorizon.saga.handler.SagaHandler;
import br.com.eventhorizon.saga.handler.SagaSingleHandler;
import br.com.eventhorizon.saga.messaging.SagaPublisher;
import br.com.eventhorizon.saga.repository.SagaRepository;
import lombok.RequiredArgsConstructor;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class SagaChain {

    private final Iterator<SagaFilter> filters;

    private final SagaHandler handler;

    private final SagaRepository repository;

    private final SagaPublisher publisher;

    private final SagaContentChecker checker;

    private final Map<Class<?>, SagaContentSerializer> serializers;

    private final SagaOptions options;

    public SagaRepository repository() {
        return repository;
    }

    public SagaPublisher publisher() {
        return publisher;
    }

    public SagaContentChecker checker() {
        return checker;
    }

    public SagaOptions options() {
        return options;
    }

    public Map<Class<?>, SagaContentSerializer> serializers() {
        return serializers;
    }

    public SagaOutput next(List<SagaMessage> messages) throws Exception {
        return doNext(messages);
    }

    private SagaOutput doNext(List<SagaMessage> messages) throws Exception {
        if (filters.hasNext()) {
            return filters.next().filter(messages, this);
        } else if (handler instanceof SagaBulkHandler) {
            return ((SagaBulkHandler) handler).handle(messages);
        } else {
            var builder = SagaOutput.builder();
            for (var message : messages) {
                var output = ((SagaSingleHandler) handler).handle(message);
                if (output != null) {
                    builder.response(output.response()).events(output.events());
                }
            }
            return builder.build();
        }
    }
}
