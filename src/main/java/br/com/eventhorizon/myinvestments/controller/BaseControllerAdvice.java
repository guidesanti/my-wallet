package br.com.eventhorizon.myinvestments.controller;

import br.com.eventhorizon.myinvestments.dto.Response;
import br.com.eventhorizon.myinvestments.dto.ResponseError;
import br.com.eventhorizon.myinvestments.dto.ResponseErrorCode;

import br.com.eventhorizon.myinvestments.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class BaseControllerAdvice {

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  protected ResponseEntity<Response<Void>> resourceNotFound(Exception ex) {
//    log.error(ex.getMessage(), ex);
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new Response<>(br.com.eventhorizon.myinvestments.dto.ResponseStatus.CLIENT_ERROR,
                    null, new ResponseError(ResponseErrorCode.RESOURCE_NOT_FOUND, ex.getMessage(), null)));
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected ResponseEntity<Response<Void>> internalServerError(Exception ex) {
    log.error(ex.getMessage(), ex);
    return ResponseEntity.internalServerError()
        .body(new Response<>(br.com.eventhorizon.myinvestments.dto.ResponseStatus.SERVER_ERROR,
            null, new ResponseError(ResponseErrorCode.UNKNOWN, ex.getMessage(), null)));
  }
}
