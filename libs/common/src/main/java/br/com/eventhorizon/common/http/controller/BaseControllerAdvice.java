package br.com.eventhorizon.common.http.controller;

import br.com.eventhorizon.common.http.Response;
import br.com.eventhorizon.common.refusal.DefaultRefusals;
import br.com.eventhorizon.common.exception.ClientException;
import br.com.eventhorizon.common.exception.BusinessException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class BaseControllerAdvice {

    // ============================================================================================================== //
    // BUSINESS ERRORS
    // - Violated business rules
    // ============================================================================================================== //

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<Response> businessError(BusinessException ex) {
        log.error("REFUSED: {}", ex.getRefusal());
        return ResponseEntity
                .unprocessableEntity()
                .body(Response.refused(ex.getRefusal()));
    }

    // ============================================================================================================== //
    // CLIENT ERRORS
    // - Validation error like field presence, not allowed values, invalid format, etc
    // ============================================================================================================== //

    @ExceptionHandler(ClientException.class)
    protected ResponseEntity<Response> clientError(ClientException ex) {
        log.error("REFUSED: {}", ex.getRefusal());
        return ResponseEntity
                .badRequest()
                .body(Response.refused(ex.getRefusal()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Response> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("REFUSED: {}", ex.getMessage(), ex);
        var message = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> message
                .append("field=").append(fieldError.getField())
                .append(", message=").append(fieldError.getDefaultMessage())
        );
        return ResponseEntity
                .badRequest()
                .body(Response.refused(
                        DefaultRefusals.BAD_REQUEST.getCode().toString(),
                        message.toString()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Response> constraintViolationException(ConstraintViolationException ex) {
        log.error("REFUSED: {}", ex.getMessage(), ex);
        var message = new StringBuilder();
        ex.getConstraintViolations().forEach(constraintViolation -> message
                .append("property=").append(constraintViolation.getPropertyPath())
                .append(", message=").append(constraintViolation.getMessage())
        );
        return ResponseEntity
                .badRequest()
                .body(Response.refused(
                        DefaultRefusals.BAD_REQUEST.getCode().toString(),
                        message.toString()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Response> httpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("REFUSED: {}", ex.getMessage(), ex);
        return ResponseEntity
                .badRequest()
                .body(Response.refused(
                        DefaultRefusals.BAD_REQUEST.getCode().toString(),
                        ex.getMessage()));
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    protected ResponseEntity<Response> missingRequestHeaderException(Exception ex) {
        log.error("REFUSED: {}", ex.getMessage(), ex);
        return ResponseEntity
                .badRequest()
                .body(Response.refused(
                        DefaultRefusals.BAD_REQUEST.getCode().toString(),
                        ex.getMessage()));
    }

    // ============================================================================================================== //
    // SYSTEM ERRORS
    // - System errors like IO errors (disk, network, downstream services unavailable or unhealthy, etc)
    // - Bad implementation resulting in bugs like NullPointerException
    // - Any other unhandled runtime exceptions
    // ============================================================================================================== //

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Response> unexpectedException(Exception ex) {
        log.error("FAILURE: {}", ex.getMessage(), ex);
        return ResponseEntity.internalServerError().body(Response.failure());
    }
}
