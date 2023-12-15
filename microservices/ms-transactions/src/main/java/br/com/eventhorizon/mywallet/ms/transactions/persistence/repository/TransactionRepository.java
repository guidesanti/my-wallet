package br.com.eventhorizon.mywallet.ms.transactions.persistence.repository;

import br.com.eventhorizon.common.repository.DuplicateKeyException;
import br.com.eventhorizon.mywallet.ms.transactions.business.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository {

    Transaction create(Transaction asset);

    Transaction update(Transaction asset);

    void delete(String id);

    List<Transaction> findAll();

    Optional<Transaction> findOne(String id);
}
