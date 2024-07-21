package org.retrolauncher.backend.app.games.infrastructure.desktop.dtos;

import org.retrolauncher.backend.app.games.application.dtos.GameSearchResult;

import java.util.UUID;

public class ListGameResponse extends GameSearchResult {
    public ListGameResponse(
            UUID id,
            String name,
            String platformName,
            String iconPath
    ) {
        super(id, name, platformName, iconPath);
    }
}
