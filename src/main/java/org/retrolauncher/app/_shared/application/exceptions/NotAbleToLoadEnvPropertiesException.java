package org.retrolauncher.app._shared.application.exceptions;

public class NotAbleToLoadEnvPropertiesException extends RuntimeException {
    private final static String MESSAGE = "Unable to load env file";

    public NotAbleToLoadEnvPropertiesException(Exception cause) {
        super(MESSAGE, cause);
    }
}
