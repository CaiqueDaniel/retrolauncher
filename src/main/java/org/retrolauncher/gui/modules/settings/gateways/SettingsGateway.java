package org.retrolauncher.gui.modules.settings.gateways;

import org.retrolauncher.gui.modules.settings.models.Settings;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface SettingsGateway {
    Optional<Settings> get();

    CompletableFuture<Void> save(Settings settings);
}
