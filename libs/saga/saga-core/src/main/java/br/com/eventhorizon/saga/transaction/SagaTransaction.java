package br.com.eventhorizon.saga.transaction;

import br.com.eventhorizon.saga.SagaOption;
import br.com.eventhorizon.saga.chain.filter.SagaFilter;
import br.com.eventhorizon.saga.content.checker.SagaContentChecker;
import br.com.eventhorizon.saga.content.serialization.SagaContentSerializer;
import br.com.eventhorizon.saga.handler.SagaHandler;
import br.com.eventhorizon.saga.messaging.publisher.SagaPublisher;
import br.com.eventhorizon.saga.repository.SagaRepository;

import java.util.List;
import java.util.Map;

public interface SagaTransaction<T> {

    String getId();

    String getMessagingProviderName();

    String getSource();

    Class<T> getSourceType();

    SagaHandler getHandler();

    SagaRepository getRepository();

    SagaPublisher getPublisher();

    List<SagaFilter> getFilters();

    SagaContentSerializer getSerializer();

    SagaContentChecker getChecker();

    Map<SagaOption, Object> getOptions();
}
