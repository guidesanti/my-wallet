package br.com.eventhorizon.myinvestments.config;

import br.com.eventhorizon.myinvestments.repository.BaseRepositoryImpl;
import br.com.eventhorizon.myinvestments.SettingsKey;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        value = "br.com.eventhorizon.myinvestments.repository",
        repositoryBaseClass = BaseRepositoryImpl.class)
@Slf4j
public class MongoConfig extends AbstractMongoClientConfiguration {

    private final String mongoUri;

    private final String mongoDbName;

    @Autowired
    public MongoConfig(Environment environment) {
        this.mongoUri = environment.getProperty(SettingsKey.MONGO_URI, String.class);
        this.mongoDbName = environment.getProperty(SettingsKey.MONGO_DB_NAME, String.class);
    }

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(this.mongoUri);
    }

    @Bean
    MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, this.mongoDbName);
    }

    @Override
    protected String getDatabaseName() {
        return this.mongoDbName;
    }
}
