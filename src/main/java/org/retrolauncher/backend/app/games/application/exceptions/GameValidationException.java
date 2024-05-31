package org.retrolauncher.backend.app.games.application.exceptions;

import org.retrolauncher.backend.app._shared.domain.exceptions.EntityValidationException;

import java.util.Map;

public class GameValidationException extends EntityValidationException {
    public GameValidationException(Map<String, String> errors) {
        super("Was not possible to attribute values for Game entity", errors);
    }
}
