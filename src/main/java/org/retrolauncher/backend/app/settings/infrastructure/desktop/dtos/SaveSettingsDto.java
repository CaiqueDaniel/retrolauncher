package org.retrolauncher.backend.app.settings.infrastructure.desktop.dtos;

public record SaveSettingsDto(
        String romsFolderPath,
        String retroarchFolderPath
) {
}
