package org.retrolauncher.gui.modules.games.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.nio.file.Path;
import java.util.Optional;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class Game {
    private final String id;
    private final String name;
    private final String platformName;
    private final Path iconPath;

    public Optional<Path> iconPath() {
        return Optional.ofNullable(iconPath);
    }
}
