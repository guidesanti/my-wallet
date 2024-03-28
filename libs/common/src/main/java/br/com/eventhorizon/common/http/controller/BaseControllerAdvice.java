package br.com.eventhorizon.common.http.controller;

import br.com.eventhorizon.common.http.Response;
import br.com.eventhorizon.common.error.DefaultErrors;
import br.com.eventhorizon.common.exception.ClientErrorException;
import br.com.eventhorizon.common.exception.BusinessErrorException;
import br.com.eventhorizon.common.exception.ServerErrorException;
import br.com.eventhorizon.common.error.ErrorCategory;
import br.com.eventhorizon.common.utils.LogUtils;
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

    @ExceptionHandler(BusinessErrorException.class)
    protected ResponseEntity<Response> businessError(BusinessErrorException ex) {
        log.info(LogUtils.buildErrorLogMessage(ex));
        return ResponseEntity
                .unprocessableEntity()
                .body(Response.error(ErrorCategory.BUSINESS_ERROR, ex.getError()));
    }

    // ============================================================================================================== //
    // CLIENT ERRORS
    // - Validation error like field presence, not allowed values, invalid format, etc
    // ============================================================================================================== //

    @ExceptionHandler(ClientErrorException.class)
    protected ResponseEntity<Response> clientError(ClientErrorException ex) {
        log.warn(LogUtils.buildErrorLogMessage(ex));
        return ResponseEntity
                .badRequest()
                .body(Response.error(ErrorCategory.CLIENT_ERROR, ex.getError()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Response> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.warn("Client error: ", ex);
        var message = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> message
                .append("field=").append(fieldError.getField())
                .append(", message=").append(fieldError.getDefaultMessage())
        );

        return ResponseEntity
                .badRequest()
                .body(Response.error(ErrorCategory.CLIENT_ERROR,
                        DefaultErrors.BAD_REQUEST.getCode().toString(),
                        message.toString()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Response> constraintViolationException(ConstraintViolationException ex) {
        log.warn("Client error: ", ex);
        var message = new StringBuilder();
        ex.getConstraintViolations().forEach(constraintViolation -> message
                .append("property=").append(constraintViolation.getPropertyPath())
                .append(", message=").append(constraintViolation.getMessage())
        );
        return ResponseEntity
                .badRequest()
                .body(Response.error(ErrorCategory.CLIENT_ERROR,
                        DefaultErrors.BAD_REQUEST.getCode().toString(),
                        message.toString()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Response> httpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.warn("Client error: ", ex);
        return ResponseEntity
                .badRequest()
                .body(Response.error(ErrorCategory.CLIENT_ERROR,
                        DefaultErrors.BAD_REQUEST.getCode().toString(),
                        ex.getMessage()));
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    protected ResponseEntity<Response> missingRequestHeaderException(Exception ex) {
        log.warn("Client error: ", ex);
        return ResponseEntity
                .badRequest()
                .body(Response.error(ErrorCategory.CLIENT_ERROR,
                        DefaultErrors.BAD_REQUEST.getCode().toString(),
                        ex.getMessage()));
    }

    // ============================================================================================================== //
    // SERVER ERRORS
    // - Explicit server errors like IO errors (disk, network, downstream services unavailable or unhealthy, etc)
    // - Bad implementation resulting in bugs like NullPointerException
    // - Other unhandled runtime exceptions
    // ============================================================================================================== //

    @ExceptionHandler(ServerErrorException.class)
    protected ResponseEntity<Response> serverError(ServerErrorException ex) {
        log.error(LogUtils.buildErrorLogMessage(ex), ex);
        return ResponseEntity
                .internalServerError()
                .body(Response.error(ErrorCategory.SERVER_ERROR, ex.getError()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Response> unexpectedException(Exception ex) {
        log.error("Unexpected exception: " + ex.getMessage(), ex);
        return ResponseEntity
                .internalServerError()
                .body(Response.error(ErrorCategory.SERVER_ERROR, DefaultErrors.UNEXPECTED_SERVER_ERROR));
    }
}
