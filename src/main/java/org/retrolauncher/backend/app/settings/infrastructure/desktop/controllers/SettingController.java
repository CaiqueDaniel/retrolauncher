package org.retrolauncher.backend.app.settings.infrastructure.desktop.controllers;

import org.retrolauncher.backend.app.settings.application.dtos.GetSettingsOutputDto;
import org.retrolauncher.backend.app.settings.application.dtos.SaveSettingsInputDto;
import org.retrolauncher.backend.app.settings.application.usecases.GetSettingsUseCase;
import org.retrolauncher.backend.app.settings.infrastructure.desktop.dtos.SaveSettingsDto;
import org.retrolauncher.backend.app.settings.application.usecases.SaveSettingsUseCase;
import org.retrolauncher.backend.app.settings.infrastructure.desktop.dtos.SettingsOutputDto;

public class SettingController {
    private final SaveSettingsUseCase saveSettingsUseCase;
    private final GetSettingsUseCase getSettingsUseCase;

    public SettingController(SaveSettingsUseCase saveSettingsUseCase, GetSettingsUseCase getSettingsUseCase) {
        this.saveSettingsUseCase = saveSettingsUseCase;
        this.getSettingsUseCase = getSettingsUseCase;
    }

    public void save(SaveSettingsDto dto) {
        this.saveSettingsUseCase.execute(new SaveSettingsInputDto(dto.romsFolderPath(), dto.retroarchFolderPath()));
    }

    public SettingsOutputDto get() {
        GetSettingsOutputDto dto = this.getSettingsUseCase.execute();
        return new SettingsOutputDto(dto.romsFolderPath(), dto.retroarchFolderPath());
    }
}
