package org.retrolauncher.backend.app.settings.application.dtos;

public record SettingsOutputDto(
        String romsFolderPath,
        String retroarchFolderPath
) {
}
