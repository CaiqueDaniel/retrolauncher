package org.retrolauncher.gui.modules.games.models;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.nio.file.Path;
import java.util.*;

@Getter
public class Game {
    private final UUID id;
    @Setter
    private String name;
    @Setter
    private String platformName;
    private Path iconPath;

    public Game(UUID id, String name, String platformName, Path iconPath) {
        this.id = id;
        this.name = name;
        this.platformName = platformName;
        this.iconPath = iconPath;
    }

    public void replaceIcon(File file) {
        if (!file.exists())
            return;
        iconPath = file.toPath();
    }

    public Optional<Path> getIconPath() {
        return Optional.ofNullable(iconPath);
    }
}
