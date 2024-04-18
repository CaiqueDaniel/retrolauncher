package org.retrolauncher.backend.app.settings.domain.exceptions;

import org.retrolauncher.backend.app._shared.domain.exceptions.EntityValidationException;

public class InvalidROMPathException extends EntityValidationException {
    private static final String MESSAGE = "ROM folder path is invalid";

    public InvalidROMPathException() {
        super(MESSAGE);
    }
}
