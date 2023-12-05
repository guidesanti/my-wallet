package br.com.eventhorizon.mywallet.common.saga.chain.filter;

import br.com.eventhorizon.mywallet.common.saga.SagaOutput;
import br.com.eventhorizon.mywallet.common.saga.SagaMessage;
import br.com.eventhorizon.mywallet.common.saga.chain.SagaChain;

import java.util.List;

public interface SagaFilter {

    int order();

    SagaOutput filter(List<SagaMessage> requests, SagaChain chain) throws Exception;
}
