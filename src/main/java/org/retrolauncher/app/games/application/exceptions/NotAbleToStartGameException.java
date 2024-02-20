package org.retrolauncher.app.games.application.exceptions;

import org.retrolauncher.app._shared.application.exceptions.NotAbleToLaunchProcessException;

public class NotAbleToStartGameException extends NotAbleToLaunchProcessException {
    private static final String MESSAGE = "Unable to launch this game";

    public NotAbleToStartGameException(Exception cause) {
        super(MESSAGE, cause);
    }
}
