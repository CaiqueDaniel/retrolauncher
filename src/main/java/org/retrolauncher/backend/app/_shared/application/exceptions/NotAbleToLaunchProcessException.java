package org.retrolauncher.backend.app._shared.application.exceptions;

public class NotAbleToLaunchProcessException extends RuntimeException {
    private static final String MESSAGE = "It was not possible to launch this process";

    public NotAbleToLaunchProcessException(Exception cause) {
        super(MESSAGE, cause);
    }

    public NotAbleToLaunchProcessException(String message, Exception cause) {
        super(message, cause);
    }
}
