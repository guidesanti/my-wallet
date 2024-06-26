package br.com.eventhorizon.common.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(staticName = "of")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseRefusal {

    @NonNull
    String code;

    @NonNull
    String message;
    
    String additionalInformation;
}
