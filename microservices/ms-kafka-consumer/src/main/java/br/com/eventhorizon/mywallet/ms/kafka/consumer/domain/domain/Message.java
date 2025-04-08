package br.com.eventhorizon.mywallet.ms.kafka.consumer.domain.domain;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Message {

    private byte[] content;
}
