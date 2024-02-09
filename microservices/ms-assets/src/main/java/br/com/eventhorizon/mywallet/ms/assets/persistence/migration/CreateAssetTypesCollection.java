package br.com.eventhorizon.mywallet.ms.assets.persistence.migration;

import br.com.eventhorizon.mywallet.ms.transactions.persistence.model.TransactionDocument;
import io.mongock.api.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

@RequiredArgsConstructor
@ChangeUnit(id="createTransactionsCollection", order="1")
public class CreateAssetTypesCollection {

    private static final String ASSETS_COLLECTION_NAME = "transactions";

    private final MongoTemplate mongoTemplate;

    @BeforeExecution
    public void before() {
        if (!mongoTemplate.getCollectionNames().contains(ASSETS_COLLECTION_NAME)) {
            mongoTemplate.createCollection(ASSETS_COLLECTION_NAME);
        }
    }

    @RollbackBeforeExecution
    public void rollbackBefore() {
        if (mongoTemplate.getCollectionNames().contains(ASSETS_COLLECTION_NAME)) {
            mongoTemplate.dropCollection(ASSETS_COLLECTION_NAME);
        }
    }
    @Execution
    public void migrationMethod() {
        var createdAtIndexDefinition = new Index("createdAt", Sort.Direction.ASC).named("createdAt");
        mongoTemplate.indexOps(TransactionDocument.class).ensureIndex(createdAtIndexDefinition);
        var settledAtIndexDefinition = new Index("settledAt", Sort.Direction.ASC).named("settledAt");
        mongoTemplate.indexOps(TransactionDocument.class).ensureIndex(settledAtIndexDefinition);
    }
    @RollbackExecution
    public void rollback() {
        mongoTemplate.indexOps(TransactionDocument.class).dropIndex("createdAt");
        mongoTemplate.indexOps(TransactionDocument.class).dropIndex("settledAt");
    }
}
