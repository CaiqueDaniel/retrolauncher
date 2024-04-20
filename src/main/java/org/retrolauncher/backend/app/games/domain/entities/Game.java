package org.retrolauncher.backend.app.games.domain.entities;

import lombok.Getter;
import org.retrolauncher.backend.app._shared.domain.entities.Entity;
import org.retrolauncher.backend.app.platforms.domain.entities.Platform;

import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Getter
public class Game extends Entity {
    private final String name;
    private Path path;
    private Path iconPath;
    private final Platform platform;

    public Game(String name, Path path, Platform platform) {
        super(UUID.randomUUID());
        this.name = name;
        this.path = path;
        this.platform = platform;
    }

    public Game(UUID id, String name, Path path, Path iconPath, Platform platform) {
        super(id);
        this.name = name;
        this.path = path;
        this.iconPath = iconPath;
        this.platform = platform;
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
}
