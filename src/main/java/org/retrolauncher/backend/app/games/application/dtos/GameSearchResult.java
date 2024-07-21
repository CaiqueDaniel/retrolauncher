package org.retrolauncher.backend.app.games.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Optional;
import java.util.UUID;

@Accessors(fluent = true)
@Getter
@AllArgsConstructor()
public class GameSearchResult {
    private final UUID id;
    private final String name;
    private final String platformName;
    private final String iconPath;

    public Optional<String> iconPath() {
        return Optional.ofNullable(this.iconPath);
    }
}
