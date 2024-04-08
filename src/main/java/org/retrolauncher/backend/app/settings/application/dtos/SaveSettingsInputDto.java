package org.retrolauncher.backend.app.settings.application.dtos;

public record SaveSettingsInputDto(
        String romsFolderPath,
        String retroarchFolderPath
) {
}
