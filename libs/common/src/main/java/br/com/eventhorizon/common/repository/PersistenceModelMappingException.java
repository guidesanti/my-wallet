package br.com.eventhorizon.common.repository;

public class PersistenceModelMappingException extends RuntimeException {

    public PersistenceModelMappingException(String message) {
        super(message);
    }

    public PersistenceModelMappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
