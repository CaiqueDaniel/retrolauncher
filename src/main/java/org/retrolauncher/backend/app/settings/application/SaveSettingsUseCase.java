package org.retrolauncher.backend.app.settings.application;

import org.retrolauncher.backend.app.settings.domain.entities.Setting;
import org.retrolauncher.backend.app.settings.domain.repositories.SettingRepository;
import org.retrolauncher.backend.app.settings.dtos.SaveSettingsInputDto;

public class SaveSettingsUseCase {
    private final SettingRepository repository;

    public SaveSettingsUseCase(SettingRepository repository) {
        this.repository = repository;
    }

    public void execute(SaveSettingsInputDto dto) {
        this.repository.save(new Setting(dto));
    }
}
