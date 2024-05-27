package org.retrolauncher.backend.app._shared.application.exceptions;

public class NotAbleToLoadEnvPropertiesException extends RuntimeException {
    private static final String MESSAGE = "Unable to load env file";

    public NotAbleToLoadEnvPropertiesException(Exception cause) {
        super(MESSAGE, cause);
    }
}
