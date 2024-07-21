package org.retrolauncher.backend.app.games.domain.entities;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.retrolauncher.backend.app._shared.domain.entities.Entity;
import org.retrolauncher.backend.app.games.application.exceptions.GameValidationException;
import org.retrolauncher.backend.app.games.domain.validators.GameValidator;
import org.retrolauncher.backend.app.platforms.domain.entities.Platform;

import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Getter
@Accessors(chain = true)
public class Game extends Entity {
    private String name;
    private Path path;
    private Path iconPath;
    private final UUID platformId;

    public Game(String name, Path path, UUID platformId) {
        super(UUID.randomUUID());
        this.name = name;
        this.path = path;
        this.platformId = platformId;
        validate();
    }

    public Game(UUID id, String name, Path path, Path iconPath, UUID platformId) {
        super(id);
        this.name = name;
        this.path = path;
        this.iconPath = iconPath;
        this.platformId = platformId;
        validate();
    }

    public void uploadIcon(Path path) {
        this.iconPath = path.toAbsolutePath();
    }

    public void updatePath(Path path) {
        this.path = path.toAbsolutePath();
    }

    public Optional<Path> getIconPath() {
        return Optional.ofNullable(this.iconPath);
    }

    public Game setName(String value) {
        this.name = value;
        validate();
        return this;
    }

    private void validate() {
        final GameValidator validator = new GameValidator(this);

        if (validator.hasErrors())
            throw new GameValidationException(validator.getErrors());
    }
}
