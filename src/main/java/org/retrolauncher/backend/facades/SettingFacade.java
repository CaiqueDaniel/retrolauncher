package org.retrolauncher.backend.facades;

import org.retrolauncher.backend.app.settings.infrastructure.desktop.dtos.SaveSettingsDto;

public interface SettingFacade {
    void save(SaveSettingsDto dto);
}
