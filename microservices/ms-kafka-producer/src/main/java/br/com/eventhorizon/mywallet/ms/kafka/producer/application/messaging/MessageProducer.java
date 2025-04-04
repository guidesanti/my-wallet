package br.com.eventhorizon.mywallet.ms.kafka.producer.application.messaging;

import br.com.eventhorizon.mywallet.ms.kafka.producer.domain.domain.Message;

public interface MessageProducer {

    void produceBatchMarkerMessage(String destination, Message message, BatchMarker batchMarker);

    void produce(String destination, Message message);

    enum BatchMarker {
        START,
        END
    }
}
