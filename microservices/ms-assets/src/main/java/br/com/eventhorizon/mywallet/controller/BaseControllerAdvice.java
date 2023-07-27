package br.com.eventhorizon.mywallet.controller;

import br.com.eventhorizon.mywallet.api.model.ErrorCode;
import br.com.eventhorizon.mywallet.api.model.Response;
import br.com.eventhorizon.mywallet.api.model.ResponseError;
import br.com.eventhorizon.mywallet.exception.ResourceNotFoundException;

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
  protected ResponseEntity<Response> resourceNotFound(Exception ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new Response(br.com.eventhorizon.mywallet.api.model.ResponseStatus.ERROR)
                    .error(new ResponseError(ErrorCode.RESOURCE_NOT_FOUND, ex.getMessage())));
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected ResponseEntity<Response> internalServerError(Exception ex) {
    log.error(ex.getMessage(), ex);
    return ResponseEntity.internalServerError()
        .body(new Response(br.com.eventhorizon.mywallet.api.model.ResponseStatus.ERROR)
                .error(new ResponseError(ErrorCode.SERVER_ERROR, ex.getMessage())));
  }
}
