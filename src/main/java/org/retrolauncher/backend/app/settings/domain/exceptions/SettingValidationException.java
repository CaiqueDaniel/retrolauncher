package org.retrolauncher.backend.app.settings.domain.exceptions;

import org.retrolauncher.backend.app._shared.domain.exceptions.EntityValidationException;

import java.util.Map;

public class SettingValidationException extends EntityValidationException {
    public SettingValidationException(Map<String, String> errors) {
        super("Was not possible to attribute values for Setting entity", errors);
    }
}
