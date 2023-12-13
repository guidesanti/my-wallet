package br.com.eventhorizon.mywallet.ms.transactions.persistence.repository.impl;

import br.com.eventhorizon.common.repository.BaseRepository;
import br.com.eventhorizon.mywallet.ms.transactions.persistence.model.TransactionDocument;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDBTransactionRepository extends BaseRepository<TransactionDocument, String> {
}
