package br.com.eventhorizon.messaging.provider.kafka.subscriber;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@RequiredArgsConstructor
public class KafkaSubscriberPolledMessageBatchStatus {

    private ProcessingStatus processingStatus = ProcessingStatus.AWAITING_PROCESSING;

    private long lastProcessedOffset = -1;

    private long lastCommittedOffset = -1;

    public boolean isFinished() {
        return processingStatus == ProcessingStatus.FINISHED
                && (lastCommittedOffset == lastProcessedOffset + 1);
    }

    enum ProcessingStatus {
        AWAITING_PROCESSING,
        PROCESSING,
        FINISHED
    }
}
