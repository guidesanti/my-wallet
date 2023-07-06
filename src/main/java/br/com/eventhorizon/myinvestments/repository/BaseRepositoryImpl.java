package br.com.eventhorizon.myinvestments.repository;

import br.com.eventhorizon.myinvestments.model.BaseModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
        obj.setId(null);
        var now = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
        obj.setCreatedAt(now);
        obj.setUpdatedAt(now);
        return super.insert(obj);
    }

    @Override
    public <S extends T> S update(S obj) {
        obj.setCreatedAt(null);
        obj.setUpdatedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> dataMap = objectMapper.convertValue(obj, Map.class);
        dataMap.values().removeIf(Objects::isNull);

        Query query = new Query().addCriteria(Criteria.where("_id").is(obj.getId()));
        Update update = new Update();
        dataMap.forEach(update::set);

        return (S) mongoOperations.findAndModify(query, update, obj.getClass());
    }

}
