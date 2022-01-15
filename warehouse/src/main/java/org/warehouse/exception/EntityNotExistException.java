package org.warehouse.exception;

public class EntityNotExistException extends RuntimeException {

    public EntityNotExistException(String message) {
        super(message); 
    }

    public EntityNotExistException() {
    }
}
