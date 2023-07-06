package br.com.eventhorizon.myinvestments.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Response<T>(
        ResponseStatus status,
        T data,
        ResponseError error) {
}
