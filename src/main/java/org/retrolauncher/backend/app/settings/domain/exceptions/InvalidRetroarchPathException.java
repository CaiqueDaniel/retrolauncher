package org.retrolauncher.backend.app.settings.domain.exceptions;

import org.retrolauncher.backend.app._shared.domain.exceptions.EntityValidationException;

public class InvalidRetroarchPathException extends EntityValidationException {
    private static final String MESSAGE = "Retroarch folder path is invalid";

    public InvalidRetroarchPathException() {
        super(MESSAGE);
    }
}
