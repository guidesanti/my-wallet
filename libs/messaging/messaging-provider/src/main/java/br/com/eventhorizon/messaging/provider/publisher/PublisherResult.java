package br.com.eventhorizon.messaging.provider.publisher;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PublisherResult {

    boolean isOk;
}
