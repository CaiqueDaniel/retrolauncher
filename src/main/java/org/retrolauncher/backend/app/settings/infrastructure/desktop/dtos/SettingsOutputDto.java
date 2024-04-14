package org.retrolauncher.backend.app.settings.infrastructure.desktop.dtos;

public record SettingsOutputDto(
        String romsFolderPath,
        String retroarchFolderPath
) {
}
