package org.retrolauncher.backend.app.games.domain.validators;

import org.retrolauncher.backend.app._shared.domain.validators.Validator;
import org.retrolauncher.backend.app.games.domain.entities.Game;

public class GameValidator extends Validator {
    private final Game game;

    public GameValidator(Game game) {
        this.game = game;
    }

    public boolean hasErrors() {
        validateName();
        validatePath();
        return !errors.isEmpty();
    }

    private void validateName() {
        if (game.getName() == null || game.getName().trim().isEmpty())
            errors.put("name", "Name cannot be empty");
    }

    private void validatePath() {
        if (game.getPath() == null)
            errors.put("path", "Path does not exists");
    }
}
