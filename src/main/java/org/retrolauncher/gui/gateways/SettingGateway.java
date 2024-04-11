package org.retrolauncher.gui.gateways;

import org.retrolauncher.backend.app.settings.infrastructure.desktop.dtos.SaveSettingsDto;
import org.retrolauncher.backend.app.settings.infrastructure.desktop.dtos.SettingsOutputDto;
import org.retrolauncher.backend.facades.SettingFacade;
import org.retrolauncher.backend.facades.SettingFacadeImpl;
import org.retrolauncher.gui.models.Setup;

import java.util.Optional;

public class SettingGateway {
    private final SettingFacade settingFacade;

    public SettingGateway() {
        this.settingFacade = new SettingFacadeImpl();
    }

    public void save(Setup setup) {
        this.settingFacade.save(new SaveSettingsDto(setup.romPath(), setup.retroarchPath()));
    }

    public Optional<Setup> get() {
        try {
            SettingsOutputDto dto = this.settingFacade.get();
            return Optional.of(new Setup(dto.romsFolderPath(), dto.retroarchFolderPath()));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
