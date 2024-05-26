package org.retrolauncher.gui.config;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.retrolauncher.gui.modules.games.pages.GamesPage;
import org.retrolauncher.gui.modules.settings.pages.SettingsPage;

@Accessors(fluent = true)
public class DependencyInjector {
    private static DependencyInjector instance;

    @Getter
    private final GamesPage gamesPage = new GamesPage();

    @Getter
    private final SettingsPage settingsPage = new SettingsPage();

    public static DependencyInjector getInstance() {
        if (instance == null)
            instance = new DependencyInjector();
        return instance;
    }
}
