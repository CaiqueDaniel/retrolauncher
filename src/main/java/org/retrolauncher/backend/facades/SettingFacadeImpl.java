package org.retrolauncher.backend.facades;

import org.retrolauncher.backend.Backend;
import org.retrolauncher.backend.app.settings.infrastructure.desktop.dtos.SaveSettingsDto;
import org.retrolauncher.backend.app.settings.infrastructure.desktop.dtos.SettingsOutputDto;

public class SettingFacadeImpl implements SettingFacade {
    @Override
    public void save(SaveSettingsDto dto) {
        Backend.getDependencies().getSettingController().save(dto);
    }

    @Override
    public SettingsOutputDto get() {
        return Backend.getDependencies().getSettingController().get();
    }
}
