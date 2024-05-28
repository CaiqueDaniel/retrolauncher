package org.retrolauncher.gui.modules.settings.gateways;

import org.retrolauncher.backend.app.settings.infrastructure.desktop.dtos.*;
import org.retrolauncher.backend.facades.*;
import org.retrolauncher.gui.modules.settings.models.Settings;

import java.nio.file.Path;
import java.util.Optional;

public class LocalSettingsGateway implements SettingsGateway {
    private final SettingFacade facade = new SettingFacadeImpl();

    @Override
    public Optional<Settings> get() {
        try {
            final SettingsOutputDto response = facade.get();
            return Optional.of(new Settings(
                    Path.of(response.romsFolderPath()),
                    Path.of(response.retroarchFolderPath())
            ));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(Settings settings) {
        facade.save(new SaveSettingsDto(settings.getRomPath().toString(), settings.getRetroarchPath().toString()));
    }
}
