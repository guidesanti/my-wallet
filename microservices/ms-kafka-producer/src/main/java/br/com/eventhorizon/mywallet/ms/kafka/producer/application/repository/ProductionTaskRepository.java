package br.com.eventhorizon.mywallet.ms.kafka.producer.application.repository;

import br.com.eventhorizon.mywallet.ms.kafka.producer.domain.domain.ProductionTask;

import java.util.Optional;

public interface ProductionTaskRepository {

    Optional<ProductionTask> findById(String id);

    void save(ProductionTask productionTask);
}
