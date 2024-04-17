package org.retrolauncher.backend.app.games.infrastructure.desktop.dtos;

import org.retrolauncher.backend.app.games.application.dtos.ListGamesUseCaseOutput;

import java.nio.file.Path;

public class ListGameResponse extends ListGamesUseCaseOutput {
    public ListGameResponse(
            String id,
            String name,
            String platformName,
            Path iconPath
    ) {
        super(id, name, platformName, iconPath);
    }
}
