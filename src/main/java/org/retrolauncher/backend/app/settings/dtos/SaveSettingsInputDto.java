package org.retrolauncher.backend.app.settings.dtos;

public record SaveSettingsInputDto(
        String romsFolderPath,
        String retroarchFolderPath
) {
}
