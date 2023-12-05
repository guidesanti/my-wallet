package br.com.eventhorizon.mywallet.ms.transactions.config;

import br.com.eventhorizon.mywallet.common.repository.BaseRepositoryImpl;
import br.com.eventhorizon.mywallet.common.saga.repository.impl.MongoDBSagaRepository;
import br.com.eventhorizon.mywallet.ms.transactions.ApplicationProperties;
import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import io.mongock.runner.springboot.EnableMongock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongock
@EnableMongoRepositories(
        value = ApplicationProperties.REPOSITORY_PACKAGE,
        repositoryBaseClass = BaseRepositoryImpl.class)
@Import({
        MongoDBSagaRepository.class
})
public class MongoConfig {

    @Bean
    public MongoTransactionManager transactionManager(MongoDatabaseFactory mongoDatabaseFactory) {
        TransactionOptions transactionalOptions = TransactionOptions.builder()
                .readConcern(ReadConcern.MAJORITY)
                .readPreference(ReadPreference.primary())
                .writeConcern(WriteConcern.MAJORITY.withJournal(true))
                .build();
        var mongoTransationManager = new MongoTransactionManager(mongoDatabaseFactory, transactionalOptions);
        mongoTransationManager.setRollbackOnCommitFailure(true);
        return mongoTransationManager;
    }
}
