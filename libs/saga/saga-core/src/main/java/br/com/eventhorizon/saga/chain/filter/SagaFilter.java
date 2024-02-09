package br.com.eventhorizon.saga.chain.filter;

import br.com.eventhorizon.saga.SagaMessage;
import br.com.eventhorizon.saga.SagaOutput;
import br.com.eventhorizon.saga.chain.SagaChain;

import java.util.List;

public interface SagaFilter<R, M> {

    int order();

    SagaOutput<R> filter(List<SagaMessage<M>> requests, SagaChain<R, M> chain) throws Exception;
}
