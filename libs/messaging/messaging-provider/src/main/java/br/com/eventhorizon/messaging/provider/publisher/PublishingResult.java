package br.com.eventhorizon.messaging.provider.publisher;

import lombok.*;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PublishingResult {

    private final boolean ok;
}
