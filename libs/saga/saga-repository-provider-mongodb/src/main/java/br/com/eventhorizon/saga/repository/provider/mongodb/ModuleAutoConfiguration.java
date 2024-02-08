package br.com.eventhorizon.saga.repository.provider.mongodb;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@AutoConfiguration
public class ModuleAutoConfiguration {

    @Bean
    public MongoDBSagaRepository mongoDBSagaRepository(MongoTemplate mongoTemplate) {
        return new MongoDBSagaRepository(mongoTemplate);
    }
}
