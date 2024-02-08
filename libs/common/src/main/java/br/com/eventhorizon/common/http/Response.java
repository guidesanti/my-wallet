package br.com.eventhorizon.common.http;

import br.com.eventhorizon.common.error.ErrorCategory;
import br.com.eventhorizon.common.error.Error;
import br.com.eventhorizon.common.error.StatusCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    @NonNull
    StatusCode statusCode;

    Object data;

    ResponseError error;

    public static Response success() {
        return new Response(StatusCode.SUCCESS, null, null);
    }

    public static <T> Response success(T data) {
        return new Response(StatusCode.SUCCESS, data, null);
    }

    public static Response error(ErrorCategory errorCategory, Error error) {
        return new Response(StatusCode.ERROR, null,
                ResponseError.of(errorCategory.getValue(), error.getCode().toString(), error.getMessage(), error.getExtraDetails()));
    }

    public static Response error(ErrorCategory errorCategory, String errorCode, String errorMessage) {
        return new Response(StatusCode.ERROR, null,
                ResponseError.of(errorCategory.getValue(), errorCode, errorMessage, null));
    }

    public static Response error(ErrorCategory errorCategory, String errorCode, String errorMessage, String extraDetails) {
        return new Response(StatusCode.ERROR, null,
                ResponseError.of(errorCategory.getValue(), errorCode, errorMessage, extraDetails));
    }
}
