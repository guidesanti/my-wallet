package br.com.eventhorizon.mywallet.repository;

import br.com.eventhorizon.mywallet.model.BaseModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<T extends BaseModel, ID extends Serializable> extends MongoRepository<T, ID> {

    <S extends T> S create(S obj);

    <S extends T> S update(S obj);
}
