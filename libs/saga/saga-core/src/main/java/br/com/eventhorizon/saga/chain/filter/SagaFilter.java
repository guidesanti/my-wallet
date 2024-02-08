package br.com.eventhorizon.saga.chain.filter;

import br.com.eventhorizon.saga.SagaMessage;
import br.com.eventhorizon.saga.SagaOutput;
import br.com.eventhorizon.saga.chain.SagaChain;

import java.util.List;

public interface SagaFilter<T> {

    int order();

    SagaOutput filter(List<SagaMessage<T>> requests, SagaChain<T> chain) throws Exception;
}
