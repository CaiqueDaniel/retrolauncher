package org.retrolauncher.backend.app.games.application.exceptions;

import org.retrolauncher.backend.app._shared.application.exceptions.NotAbleToLaunchProcessException;

public class NotAbleToStartGameException extends NotAbleToLaunchProcessException {
    private static final String MESSAGE = "Unable to launch this game";

    public NotAbleToStartGameException(Exception cause) {
        super(MESSAGE, cause);
    }
}
