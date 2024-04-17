package org.retrolauncher.backend.app.games.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.nio.file.Path;
import java.util.Optional;

@Accessors(fluent = true)
@Getter
@AllArgsConstructor()
public class ListGamesUseCaseOutput {
    private final String id;
    private final String name;
    private final String platformName;
    private final Path iconPath;

    public Optional<Path> iconPath() {
        return Optional.ofNullable(this.iconPath);
    }
}
