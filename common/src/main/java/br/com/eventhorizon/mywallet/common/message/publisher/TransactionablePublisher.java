package br.com.eventhorizon.mywallet.common.message.publisher;

import br.com.eventhorizon.mywallet.common.transaction.TransactionManager;

public interface TransactionablePublisher extends Publisher, TransactionManager {
}
