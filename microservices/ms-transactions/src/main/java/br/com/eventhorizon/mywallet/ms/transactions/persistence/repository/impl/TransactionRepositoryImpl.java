package br.com.eventhorizon.mywallet.ms.transactions.persistence.repository.impl;

import br.com.eventhorizon.mywallet.ms.transactions.business.model.Transaction;
import br.com.eventhorizon.mywallet.ms.transactions.persistence.model.mapper.TransactionDocumentMapper;
import br.com.eventhorizon.mywallet.ms.transactions.persistence.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class TransactionRepositoryImpl implements TransactionRepository {

    private final MongoDBTransactionRepository mongoDBTransactionRepository;

    @Override
    public Transaction create(Transaction asset) {
        return TransactionDocumentMapper.toBusinessModel(mongoDBTransactionRepository.create(TransactionDocumentMapper.toPersistenceModel(asset)));
    }

    @Override
    public Transaction update(Transaction asset) {
        return TransactionDocumentMapper.toBusinessModel(mongoDBTransactionRepository.update(TransactionDocumentMapper.toPersistenceModel(asset)));
    }

    @Override
    public void delete(String id) {
        mongoDBTransactionRepository.deleteById(id);
    }

    @Override
    public List<Transaction> findAll() {
        return mongoDBTransactionRepository.findAll().stream()
                .map(TransactionDocumentMapper::toBusinessModel)
                .toList();
    }

    @Override
    public Optional<Transaction> findOne(String id) {
        return mongoDBTransactionRepository.findById(id).map(TransactionDocumentMapper::toBusinessModel);
    }
}
