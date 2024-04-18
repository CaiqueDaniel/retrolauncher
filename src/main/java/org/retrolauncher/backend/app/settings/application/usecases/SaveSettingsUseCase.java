package org.retrolauncher.backend.app.settings.application.usecases;

import org.retrolauncher.backend.app._shared.application.exceptions.RetroarchNotFoundException;
import org.retrolauncher.backend.app._shared.application.services.EventDispatcherService;
import org.retrolauncher.backend.app.settings.domain.entities.Setting;
import org.retrolauncher.backend.app.settings.domain.repositories.SettingRepository;
import org.retrolauncher.backend.app.settings.application.dtos.SaveSettingsInputDto;
import org.retrolauncher.backend.app._shared.application.dtos.EventType;

import java.nio.file.Path;

public class SaveSettingsUseCase {
    private final SettingRepository repository;
    private final EventDispatcherService eventDispatcherService;

    public SaveSettingsUseCase(SettingRepository repository, EventDispatcherService eventDispatcherService) {
        this.repository = repository;
        this.eventDispatcherService = eventDispatcherService;
    }

    public void execute(SaveSettingsInputDto dto) {
        if (!isRetroarchInstalled(Path.of(dto.retroarchFolderPath())))
            throw new RetroarchNotFoundException();
        Setting setting = new Setting(dto.romsFolderPath(), dto.retroarchFolderPath());
        repository.save(setting);
        eventDispatcherService.dispatch(EventType.RETROARCH_FOLDER_UPDATED);
        eventDispatcherService.dispatch(EventType.ROMS_FOLDER_UPDATED);
    }

    private boolean isRetroarchInstalled(Path retroarchFolderPath) {
        return retroarchFolderPath.toAbsolutePath().resolve("retroarch.exe").toFile().exists();
    }
}
