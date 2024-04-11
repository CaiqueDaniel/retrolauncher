package org.retrolauncher.backend.app.settings.infrastructure.desktop.controllers;

import org.retrolauncher.backend.app.settings.application.dtos.SaveSettingsInputDto;
import org.retrolauncher.backend.app.settings.infrastructure.desktop.dtos.SaveSettingsDto;
import org.retrolauncher.backend.app.settings.application.usecases.SaveSettingsUseCase;

public class SettingController {
    private final SaveSettingsUseCase saveSettingsUseCase;

    public SettingController(SaveSettingsUseCase saveSettingsUseCase) {
        this.saveSettingsUseCase = saveSettingsUseCase;
    }

    public void save(SaveSettingsDto dto) {
        this.saveSettingsUseCase.execute(new SaveSettingsInputDto(dto.romsFolderPath(), dto.retroarchFolderPath()));
    }
}
