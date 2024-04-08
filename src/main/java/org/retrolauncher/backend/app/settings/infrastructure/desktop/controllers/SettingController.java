package org.retrolauncher.backend.app.settings.infrastructure.desktop.controllers;

import org.retrolauncher.backend.app.settings.application.dtos.SaveSettingsInputDto;
import org.retrolauncher.backend.app.settings.application.usecases.SaveSettingsUseCase;

public class SettingController {
    private final SaveSettingsUseCase saveSettingsUseCase;

    public SettingController(SaveSettingsUseCase saveSettingsUseCase) {
        this.saveSettingsUseCase = saveSettingsUseCase;
    }

    public void save(SaveSettingsInputDto dto) {
        this.saveSettingsUseCase.execute(dto);
    }
}
