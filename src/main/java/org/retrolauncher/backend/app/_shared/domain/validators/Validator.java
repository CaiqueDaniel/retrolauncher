package org.retrolauncher.backend.app._shared.domain.validators;

import lombok.Getter;

import java.util.*;

@Getter
public abstract class Validator {
    protected final Map<String, String> errors = new HashMap<>();

    public abstract boolean hasErrors();
}
