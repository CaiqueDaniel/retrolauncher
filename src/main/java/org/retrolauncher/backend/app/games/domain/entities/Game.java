package org.retrolauncher.backend.app.games.domain.entities;

import lombok.Getter;
import org.retrolauncher.backend.app._shared.domain.entities.Entity;
import org.retrolauncher.backend.app.platforms.domain.entities.Platform;

import java.util.UUID;

@Getter
public class Game extends Entity {
    private final String name;
    private final String path;
    private String iconPath;
    private final Platform platform;

    public Game(String name, String path, String iconPath, Platform platform) {
        super(UUID.randomUUID());
        this.name = name;
        this.path = path;
        this.iconPath = iconPath;
        this.platform = platform;
    }

    public Game(UUID id, String name, String path, String iconPath, Platform platform) {
        super(id);
        this.name = name;
        this.path = path;
        this.iconPath = iconPath;
        this.platform = platform;
    }

    public void uploadIcon(String path) {
        this.iconPath = path;
    }
}
