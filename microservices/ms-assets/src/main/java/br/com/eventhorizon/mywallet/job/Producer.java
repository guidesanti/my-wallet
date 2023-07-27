package br.com.eventhorizon.mywallet.job;

import br.com.eventhorizon.mywallet.Application;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Producer {

  private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

//  private final KafkaProducer<String, String> producer;

  private int count = 0;

  public Producer() {
//    Properties kafkaProps = new Properties();
//    kafkaProps.put("bootstrap.servers", "kafka:9092");
//    kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//    kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//    kafkaProps.put("reconnect.backoff.max.ms", 10000L);
//    producer = new KafkaProducer<>(kafkaProps);
  }

//  @Scheduled(fixedRate = 10000, initialDelay = 1000)
//  public void produce() {
//    LOGGER.info(String.format("Producing [%d] ...", count++));
//    ProducerRecord<String, String> record =
//        new ProducerRecord<>("CustomerCountry", "Biomedical Materials", "USA");
//    producer.send(record, new ProducerCallback());
//  }

  private class ProducerCallback implements Callback {

    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
      if (e != null) {
        LOGGER.error("Send to Kafka failed");
        e.printStackTrace();
      } else {
        LOGGER.info("Send to Kafka success: " + recordMetadata);
      }
    }
  }
}
