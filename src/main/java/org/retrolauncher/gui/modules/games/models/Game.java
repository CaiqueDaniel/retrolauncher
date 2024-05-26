package org.retrolauncher.gui.modules.games.models;

import lombok.Getter;

import java.nio.file.Path;
import java.util.*;

@Getter
public class Game {
    private final UUID id;
    private final String name;
    private final String platformName;
    private final Path iconPath;

    public Game(UUID id, String name, String platformName, Path iconPath) {
        this.id = id;
        this.name = name;
        this.platformName = platformName;
        this.iconPath = iconPath;
    }

    public Optional<Path> getIconPath() {
        return Optional.ofNullable(iconPath);
    }
}
