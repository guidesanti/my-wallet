package br.com.eventhorizon.common.http.controller;

import br.com.eventhorizon.common.http.Response;
import br.com.eventhorizon.common.common.DefaultErrors;
import br.com.eventhorizon.common.exception.BaseException;
import br.com.eventhorizon.common.exception.ClientErrorException;
import br.com.eventhorizon.common.exception.BusinessErrorException;
import br.com.eventhorizon.common.exception.ServerErrorException;
import br.com.eventhorizon.common.common.ErrorCategory;
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

    // Application exceptions

    @ExceptionHandler(BusinessErrorException.class)
    protected ResponseEntity<Response> businessError(BusinessErrorException ex) {
        log.info(buildLogMessage("Business error: ", ex));
        return ResponseEntity.unprocessableEntity().body(Response.error(ErrorCategory.BUSINESS_ERROR, ex.getError()));
    }

    @ExceptionHandler(ClientErrorException.class)
    protected ResponseEntity<Response> clientError(ClientErrorException ex) {
        log.warn(buildLogMessage("Client error: ", ex));
        return ResponseEntity.unprocessableEntity().body(Response.error(ErrorCategory.CLIENT_ERROR, ex.getError()));
    }

    @ExceptionHandler(ServerErrorException.class)
    protected ResponseEntity<Response> serverError(ServerErrorException ex) {
        log.error(buildLogMessage("Server error: ", ex), ex);
        return ResponseEntity.unprocessableEntity().body(Response.error(ErrorCategory.SERVER_ERROR, ex.getError()));
    }

    // Spring validation exceptions

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Response> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.warn("Client error: ", ex);
        var message = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> message
                .append("field=").append(fieldError.getField())
                .append(", message=").append(fieldError.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(
                Response.error(ErrorCategory.CLIENT_ERROR, DefaultErrors.BAD_REQUEST.getCode(), message.toString()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Response> constraintViolationException(ConstraintViolationException ex) {
        log.warn("Client error: ", ex);
        var message = new StringBuilder();
        ex.getConstraintViolations().forEach(constraintViolation -> message
                .append("property=").append(constraintViolation.getPropertyPath())
                .append(", message=").append(constraintViolation.getMessage())
        );
        return ResponseEntity.badRequest().body(
                Response.error(ErrorCategory.CLIENT_ERROR, DefaultErrors.BAD_REQUEST.getCode(), message.toString()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Response> httpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.warn("Client error: ", ex);
        return ResponseEntity.badRequest().body(
                Response.error(ErrorCategory.CLIENT_ERROR, DefaultErrors.BAD_REQUEST.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    protected ResponseEntity<Response> missingRequestHeaderException(Exception ex) {
        log.warn("Client error: ", ex);
        return ResponseEntity.badRequest().body(
                Response.error(ErrorCategory.CLIENT_ERROR, DefaultErrors.BAD_REQUEST.getCode(), ex.getMessage()));
    }

    // Unexpected exceptions

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Response> unexpectedException(Exception ex) {
        log.error("Unexpected exception: " + ex.getMessage(), ex);
        return ResponseEntity.unprocessableEntity().body(
                Response.error(ErrorCategory.SERVER_ERROR, DefaultErrors.UNEXPECTED_SERVER_ERROR));
    }

    private String buildLogMessage(String head, BaseException ex) {
        return head +
                "errorCode='" + ex.getError().getCode() + "' " +
                "errorMessage='" + ex.getError().getMessage() + "' " +
                "exceptionMessage='" + ex.getMessage() + "'";
    }
}
