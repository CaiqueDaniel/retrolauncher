package org.retrolauncher.gui.modules.settings.gateways;

import org.retrolauncher.backend.app.settings.infrastructure.desktop.dtos.SaveSettingsDto;
import org.retrolauncher.backend.app.settings.infrastructure.desktop.dtos.SettingsOutputDto;
import org.retrolauncher.backend.facades.SettingFacade;
import org.retrolauncher.backend.facades.SettingFacadeImpl;
import org.retrolauncher.gui.modules.settings.models.Setting;

import java.util.Optional;

public class SettingGateway {
    private final SettingFacade settingFacade;

    public SettingGateway() {
        this.settingFacade = new SettingFacadeImpl();
    }

    public void save(Setting setting) {
        this.settingFacade.save(new SaveSettingsDto(setting.romPath(), setting.retroarchPath()));
    }

    public Optional<Setting> get() {
        try {
            SettingsOutputDto dto = this.settingFacade.get();
            return Optional.of(new Setting(dto.romsFolderPath(), dto.retroarchFolderPath()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
