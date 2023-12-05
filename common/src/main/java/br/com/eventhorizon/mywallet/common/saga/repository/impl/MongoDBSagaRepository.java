package br.com.eventhorizon.mywallet.common.saga.repository.impl;

import br.com.eventhorizon.mywallet.common.saga.SagaEvent;
import br.com.eventhorizon.mywallet.common.saga.SagaResponse;
import br.com.eventhorizon.mywallet.common.saga.content.serializer.SagaContentSerializer;
import br.com.eventhorizon.mywallet.common.saga.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static br.com.eventhorizon.mywallet.common.saga.Conventions.DEFAULT_DATE_TIME_FORMATTER;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Slf4j
@Repository
public class MongoDBSagaRepository implements SagaRepository {

    private static final String DEFAULT_SAGA_TRANSACTIONS_COLLECTION_NAME = "saga-transactions";

    private static final String DEFAULT_SAGA_RESPONSES_COLLECTION_NAME = "saga-responses";

    private static final String DEFAULT_SAGA_EVENTS_COLLECTION_NAME = "saga-events";

    private static final String IDEMPOTENCE_ID_FIELD_NAME = "idempotenceId";

    private static final String IDEMPOTENCE_ID_INDEX_NAME = "idempotenceIdIndex";

    private final MongoTemplate mongoTemplate;

    public MongoDBSagaRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
        ensureIndexes();
    }

    @Override
    public boolean createTransaction(String idempotenceId, String checksum) {
        var transactionEntity = SagaTransactionEntity.builder()
                .idempotenceId(idempotenceId)
                .checksum(checksum)
                .createdAt(OffsetDateTime.now(ZoneOffset.UTC).format(DEFAULT_DATE_TIME_FORMATTER))
                .build();
        try {
            mongoTemplate.insert(transactionEntity, DEFAULT_SAGA_TRANSACTIONS_COLLECTION_NAME);
            return true;
        } catch (DuplicateKeyException ex) {
            log.warn("SAGA transaction already exists with same idempotence ID: {}", transactionEntity);
            return false;
        } catch (Exception ex) {
            log.error("Failed to create SAGA transaction on DB", ex);
            throw ex;
        }
    }

    @Override
    public SagaTransactionEntity findTransaction(String idempotenceId) {
        return mongoTemplate.findOne(Query.query(where(IDEMPOTENCE_ID_FIELD_NAME).is(idempotenceId)),
                SagaTransactionEntity.class, DEFAULT_SAGA_TRANSACTIONS_COLLECTION_NAME);
    }

    @Override
    public void createResponse(SagaResponse response, Map<Class<?>, SagaContentSerializer> serializers) {
        var responseEntity = SagaResponseMapper.modelToEntity(response, serializers.get(response.content().getContent().getClass()));
        mongoTemplate.insert(responseEntity, DEFAULT_SAGA_RESPONSES_COLLECTION_NAME);
    }

    @Override
    public SagaResponse findResponse(String idempotenceId, Map<Class<?>, SagaContentSerializer> serializers) {
        var response = mongoTemplate.findOne(Query.query(where(IDEMPOTENCE_ID_FIELD_NAME).is(idempotenceId)),
                SagaResponseEntity.class, DEFAULT_SAGA_RESPONSES_COLLECTION_NAME);
        if (response != null) {
            try {
                return SagaResponseMapper.entityToModel(response, serializers.get(Class.forName(response.getContentType())));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override
    public void createEvents(List<SagaEvent> events, Map<Class<?>, SagaContentSerializer> serializers) {
        mongoTemplate.insert(events.stream()
                        .map(e -> SagaEventMapper.modelToEntity(e, serializers.get(e.content().getContent().getClass())))
                        .collect(Collectors.toList()),
                DEFAULT_SAGA_EVENTS_COLLECTION_NAME);
    }

    @Override
    public List<SagaEvent> findEvents(String idempotenceId, Map<Class<?>, SagaContentSerializer> serializers) {
        var events = mongoTemplate.find(Query.query(where(IDEMPOTENCE_ID_FIELD_NAME).is(idempotenceId)),
                SagaEventEntity.class, DEFAULT_SAGA_EVENTS_COLLECTION_NAME);
        return events.stream().map(e -> {
            try {
                return SagaEventMapper.entityToModel(e, serializers.get(Class.forName(e.getContentType())));
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }).toList();
    }

    @Override
    @Transactional(rollbackFor = { Exception.class })
    public <T> T transact(Callable<T> task) throws Exception {
        log.info("Saga repository transaction start");
        var output = task.call();
        log.info("Saga repository transaction commit");
        return output;
    }

    private void ensureIndexes() {
        var indexDefinition = new Index(IDEMPOTENCE_ID_FIELD_NAME, Sort.Direction.ASC)
                .expire(86400)
                .named(IDEMPOTENCE_ID_INDEX_NAME)
                .unique();
        mongoTemplate.indexOps(DEFAULT_SAGA_TRANSACTIONS_COLLECTION_NAME).ensureIndex(indexDefinition);

        indexDefinition = new Index(IDEMPOTENCE_ID_FIELD_NAME, Sort.Direction.ASC)
                .expire(86400)
                .named(IDEMPOTENCE_ID_INDEX_NAME)
                .unique();
        mongoTemplate.indexOps(DEFAULT_SAGA_RESPONSES_COLLECTION_NAME).ensureIndex(indexDefinition);

        indexDefinition = new Index(IDEMPOTENCE_ID_FIELD_NAME, Sort.Direction.ASC)
                .expire(86400)
                .named(IDEMPOTENCE_ID_INDEX_NAME)
                .unique();
        mongoTemplate.indexOps(DEFAULT_SAGA_EVENTS_COLLECTION_NAME).ensureIndex(indexDefinition);
    }
}
