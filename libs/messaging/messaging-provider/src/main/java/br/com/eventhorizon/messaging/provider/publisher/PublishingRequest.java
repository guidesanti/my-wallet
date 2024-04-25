package br.com.eventhorizon.messaging.provider.publisher;

import br.com.eventhorizon.common.messaging.Message;
import lombok.*;

import java.time.Duration;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PublishingRequest<T> {

    private static final Duration DEFAULT_DELAY = Duration.ZERO;

    @NonNull
    private final String destination;

    private final String orderingKey;

    @Builder.Default
    private final Duration delay = DEFAULT_DELAY;

    @NonNull
    private final Message<T> message;
}
