package org.retrolauncher.backend.facades;

import org.retrolauncher.backend.app.settings.infrastructure.desktop.dtos.SaveSettingsDto;
import org.retrolauncher.backend.app.settings.infrastructure.desktop.dtos.SettingsOutputDto;

public interface SettingFacade {
    void save(SaveSettingsDto dto);

    SettingsOutputDto get();
}
