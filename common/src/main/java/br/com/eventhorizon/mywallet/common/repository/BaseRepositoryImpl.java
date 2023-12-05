package br.com.eventhorizon.mywallet.common.repository;

import br.com.eventhorizon.mywallet.common.util.IdUtil;
import com.fasterxml.jackson.databind.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class BaseRepositoryImpl<T extends BaseModel, ID extends Serializable> extends SimpleMongoRepository<T, ID> implements
        BaseRepository<T, ID> {

    protected MongoOperations mongoOperations;

    public BaseRepositoryImpl(MongoEntityInformation<T, ID> metadata,
                              MongoOperations mongoOperations) {
        super(metadata, mongoOperations);
        this.mongoOperations = mongoOperations;
    }

    @Transactional
    @Override
    public <S extends T> S save(S obj) {
        return update(obj);
    }

    @Override
    public <S extends T> S create(S obj) {
        if (obj.getId() == null) {
            obj.setId(IdUtil.generateId(IdUtil.IdType.UUID));
        }
        var now = OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
        if (obj.getCreatedAt() == null) {
            obj.setCreatedAt(now);
        }
        obj.setUpdatedAt(now);
        return super.insert(obj);
    }

    @Override
    public <S extends T> S update(S obj) {
        obj.setCreatedAt(null);
        obj.setUpdatedAt(OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_ZONED_DATE_TIME));

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> dataMap = objectMapper.convertValue(obj, Map.class);
        dataMap.remove("");
        dataMap.values().removeIf(Objects::isNull);
        log.info("Data Map: {}", dataMap);

        Query query = new Query().addCriteria(Criteria.where("_id").is(obj.getId()));
        log.info("Query: {}", query);

        Update update = new Update();
        dataMap.forEach(update::set);
        log.info("Update: {}", update);

        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);
        log.info("Options: {}", options);

        var result = mongoOperations.findAndModify(query, update, options, obj.getClass());
        log.info("Update results: {}", result);

        return (S) result;
    }
}
