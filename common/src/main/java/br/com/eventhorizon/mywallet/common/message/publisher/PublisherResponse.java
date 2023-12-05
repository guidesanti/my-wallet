package br.com.eventhorizon.mywallet.common.message.publisher;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PublisherResponse {

    boolean isOk;
}
