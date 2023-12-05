package br.com.eventhorizon.mywallet.common.saga.content;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(staticName = "of")
public class SagaContent {

    Object content;
}
