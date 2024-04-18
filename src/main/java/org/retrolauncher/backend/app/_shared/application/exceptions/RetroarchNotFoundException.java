package org.retrolauncher.backend.app._shared.application.exceptions;

public class RetroarchNotFoundException extends NotFoundException {
    private static final String MESSAGE = "Retroarch was not found";

    public RetroarchNotFoundException() {
        super(MESSAGE);
    }
}
