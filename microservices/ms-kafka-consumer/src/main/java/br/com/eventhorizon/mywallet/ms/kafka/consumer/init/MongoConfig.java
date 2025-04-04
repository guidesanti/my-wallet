package br.com.eventhorizon.mywallet.ms.kafka.consumer.init;

import br.com.eventhorizon.common.repository.BaseRepositoryImpl;
import br.com.eventhorizon.mywallet.ms.kafka.producer.ApplicationProperties;
import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import io.mongock.runner.springboot.EnableMongock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongock
@EnableMongoRepositories(
        value = ApplicationProperties.REPOSITORY_PACKAGE,
        repositoryBaseClass = BaseRepositoryImpl.class)
public class MongoConfig {

    @Bean
    public MongoTransactionManager transactionManager(MongoDatabaseFactory mongoDatabaseFactory) {
        TransactionOptions transactionalOptions = TransactionOptions.builder()
                .readConcern(ReadConcern.MAJORITY)
                .readPreference(ReadPreference.primary())
                .writeConcern(WriteConcern.MAJORITY.withJournal(true))
                .build();
        var mongoTransactionManager = new MongoTransactionManager(mongoDatabaseFactory, transactionalOptions);
        mongoTransactionManager.setRollbackOnCommitFailure(true);
        return mongoTransactionManager;
    }
}
