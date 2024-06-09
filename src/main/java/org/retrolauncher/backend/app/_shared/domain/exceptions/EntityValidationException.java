package org.retrolauncher.backend.app._shared.domain.exceptions;

import lombok.Getter;

import java.util.Map;

@Getter
public abstract class EntityValidationException extends RuntimeException {
    private final Map<String, String> errors;

    protected EntityValidationException(String message, Map<String, String> errors) {
        super(message);
        this.errors = Map.copyOf(errors);
    }
}
