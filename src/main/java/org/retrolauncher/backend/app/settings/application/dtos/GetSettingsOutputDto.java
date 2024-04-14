package org.retrolauncher.backend.app.settings.application.dtos;

public record GetSettingsOutputDto(
        String romsFolderPath,
        String retroarchFolderPath
) {
}
