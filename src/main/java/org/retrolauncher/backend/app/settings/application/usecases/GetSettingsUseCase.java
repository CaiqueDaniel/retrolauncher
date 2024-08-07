package org.retrolauncher.backend.app.settings.application.usecases;

import org.retrolauncher.backend.app.settings.application.exceptions.SettingNotFoundException;
import org.retrolauncher.backend.app.settings.domain.entities.Setting;
import org.retrolauncher.backend.app.settings.domain.repositories.SettingRepository;
import org.retrolauncher.backend.app.settings.application.dtos.GetSettingsOutputDto;

import java.util.Optional;

public class GetSettingsUseCase {
    private final SettingRepository repository;

    public GetSettingsUseCase(SettingRepository repository) {
        this.repository = repository;
    }

    public GetSettingsOutputDto execute() {
        Optional<Setting> result = this.repository.get();

        if (result.isEmpty())
            throw new SettingNotFoundException();

        return new GetSettingsOutputDto(
                result.get().getRomsFolderPath().toString(),
                result.get().getRetroarchFolderPath().toString()
        );
    }
}

