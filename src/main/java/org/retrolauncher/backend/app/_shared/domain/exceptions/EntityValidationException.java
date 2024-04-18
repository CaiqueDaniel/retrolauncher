package org.retrolauncher.backend.app._shared.domain.exceptions;

public abstract class EntityValidationException extends RuntimeException {
    public EntityValidationException(String message) {
        super(message);
    }
}
