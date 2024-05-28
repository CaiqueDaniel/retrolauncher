package org.retrolauncher.gui.modules.settings.gateways;

import org.retrolauncher.gui.modules.settings.models.Settings;

import java.util.Optional;

public interface SettingsGateway {
    Optional<Settings> get();
    void save(Settings settings);
}
