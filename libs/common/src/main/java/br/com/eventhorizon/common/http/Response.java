package br.com.eventhorizon.common.http;

import br.com.eventhorizon.common.refusal.Refusal;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    @NonNull
    StatusCode statusCode;

    Object data;

    ResponseRefusal refusal;

    public static Response success() {
        return new Response(StatusCode.SUCCESS, null, null);
    }

    public static <T> Response success(T data) {
        return new Response(StatusCode.SUCCESS, data, null);
    }

    public static Response refused(Refusal refusal) {
        return new Response(StatusCode.REFUSED, null,
                ResponseRefusal.of(refusal.getCode().toString(), refusal.getMessage(), refusal.getAdditionalInformation().orElse(null)));
    }

    public static Response refused(String code, String message) {
        return new Response(StatusCode.REFUSED, null,
                ResponseRefusal.of(code, message, null));
    }

    public static Response failure() {
        return new Response(StatusCode.FAILURE, null, null);
    }
}
