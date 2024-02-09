package br.com.eventhorizon.mywallet.ms.assets.persistence.migration;

import br.com.eventhorizon.mywallet.ms.assets.persistence.model.AssetTypeDocument;
import io.mongock.api.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

@RequiredArgsConstructor
@ChangeUnit(id="createAssetTypesCollection", order="1")
public class CreateAssetTypesCollection {

    private static final String ASSET_TYPES_COLLECTION_NAME = "asset-types";

    private final MongoTemplate mongoTemplate;

    @BeforeExecution
    public void before() {
        if (!mongoTemplate.getCollectionNames().contains(ASSET_TYPES_COLLECTION_NAME)) {
            mongoTemplate.createCollection(ASSET_TYPES_COLLECTION_NAME);
        }
    }

    @RollbackBeforeExecution
    public void rollbackBefore() {
        if (mongoTemplate.getCollectionNames().contains(ASSET_TYPES_COLLECTION_NAME)) {
            mongoTemplate.dropCollection(ASSET_TYPES_COLLECTION_NAME);
        }
    }
    @Execution
    public void migrationMethod() {
        var createdAtIndexDefinition = new Index("createdAt", Sort.Direction.ASC).named("createdAt");
        mongoTemplate.indexOps(AssetTypeDocument.class).ensureIndex(createdAtIndexDefinition);
        var nameIndexDefinition = new Index("name", Sort.Direction.ASC).named("name").unique();
        mongoTemplate.indexOps(AssetTypeDocument.class).ensureIndex(nameIndexDefinition);
    }
    @RollbackExecution
    public void rollback() {
        mongoTemplate.indexOps(AssetTypeDocument.class).dropIndex("createdAt");
        mongoTemplate.indexOps(AssetTypeDocument.class).dropIndex("name");
    }
}
