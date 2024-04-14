package org.retrolauncher.backend.app.settings.application.usecases;

import org.retrolauncher.backend.app._shared.application.services.EventDispatcherService;
import org.retrolauncher.backend.app.settings.domain.entities.Setting;
import org.retrolauncher.backend.app.settings.domain.repositories.SettingRepository;
import org.retrolauncher.backend.app.settings.application.dtos.SaveSettingsInputDto;
import org.retrolauncher.backend.app._shared.application.dtos.EventType;

public class SaveSettingsUseCase {
    private final SettingRepository repository;
    private final EventDispatcherService eventDispatcherService;

    public SaveSettingsUseCase(SettingRepository repository, EventDispatcherService eventDispatcherService) {
        this.repository = repository;
        this.eventDispatcherService = eventDispatcherService;
    }

    public void execute(SaveSettingsInputDto dto) {
        Setting setting = new Setting(dto.romsFolderPath(), dto.retroarchFolderPath());
        this.repository.save(setting);
        this.eventDispatcherService.dispatch(EventType.RETROARCH_FOLDER_UPDATED);
        this.eventDispatcherService.dispatch(EventType.ROMS_FOLDER_UPDATED);
    }
}
