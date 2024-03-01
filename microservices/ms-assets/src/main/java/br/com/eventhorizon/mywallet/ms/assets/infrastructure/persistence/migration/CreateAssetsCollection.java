package br.com.eventhorizon.mywallet.ms.assets.infrastructure.persistence.migration;

import br.com.eventhorizon.mywallet.ms.assets.infrastructure.persistence.model.AssetDocument;
import io.mongock.api.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

@RequiredArgsConstructor
@ChangeUnit(id="createAssetsCollection", order="1")
public class CreateAssetsCollection {

    private static final String ASSETS_COLLECTION_NAME = "assets";

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
        var shortNameIndexDefinition = new Index("shortName", Sort.Direction.ASC).unique().named("shortName");
        mongoTemplate.indexOps(AssetDocument.class).ensureIndex(shortNameIndexDefinition);
        var longNameIndexDefinition = new Index("longName", Sort.Direction.ASC).unique().named("longName");
        mongoTemplate.indexOps(AssetDocument.class).ensureIndex(longNameIndexDefinition);
    }
    @RollbackExecution
    public void rollback() {
        mongoTemplate.indexOps(AssetDocument.class).dropIndex("shortName");
        mongoTemplate.indexOps(AssetDocument.class).dropIndex("longName");
    }
}
