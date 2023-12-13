package br.com.eventhotizon.messaging.provider.publisher;

import br.com.eventhorizon.common.transaction.TransactionManager;

public interface TransactionablePublisher extends Publisher, TransactionManager {
}
