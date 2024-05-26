package org.retrolauncher.gui.modules.settings.gateways;

import org.retrolauncher.backend.app.settings.infrastructure.desktop.dtos.SaveSettingsDto;
import org.retrolauncher.backend.facades.SettingFacade;
import org.retrolauncher.backend.facades.SettingFacadeImpl;
import org.retrolauncher.gui.modules.settings.models.Settings;

public class LocalSettingsGateway implements SettingsGateway {
    private final SettingFacade facade = new SettingFacadeImpl();

    @Override
    public void save(Settings settings) {
        facade.save(new SaveSettingsDto(settings.getRomPath().toString(), settings.getRetroarchPath().toString()));
    }
}
