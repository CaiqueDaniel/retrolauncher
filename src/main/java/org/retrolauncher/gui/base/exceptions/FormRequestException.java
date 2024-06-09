package org.retrolauncher.gui.base.exceptions;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class FormRequestException extends RuntimeException {
    private Map<String, String> errors = new HashMap<>();

    public FormRequestException(String message) {
        super(message);
    }

    public FormRequestException(String message, Map<String, String> errors) {
        super(message);
        this.errors = Map.copyOf(errors);
    }
}
