package org.retrolauncher.backend.app.settings.application.exceptions;

import org.retrolauncher.backend.app._shared.application.exceptions.NotFoundException;

public class SettingNotFoundException extends NotFoundException {
    private static final String MESSAGE = "Setting not found";

    public SettingNotFoundException() {
        super(MESSAGE);
    }
}
