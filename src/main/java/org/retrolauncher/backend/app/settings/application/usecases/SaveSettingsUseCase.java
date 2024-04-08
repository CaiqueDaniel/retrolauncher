package org.retrolauncher.backend.app.settings.application.usecases;

import org.retrolauncher.backend.app.settings.domain.entities.Setting;
import org.retrolauncher.backend.app.settings.domain.repositories.SettingRepository;
import org.retrolauncher.backend.app.settings.application.dtos.SaveSettingsInputDto;

public class SaveSettingsUseCase {
    private final SettingRepository repository;

    public SaveSettingsUseCase(SettingRepository repository) {
        this.repository = repository;
    }

    public void execute(SaveSettingsInputDto dto) {
        Setting setting = new Setting(dto.romsFolderPath(), dto.retroarchFolderPath());
        this.repository.save(setting);
    }
}
