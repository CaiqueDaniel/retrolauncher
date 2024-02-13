package org.retrolauncher;

import org.retrolauncher.app._shared.application.services.EnvConfigService;

public class Main {
    private static final DependencyInjector dependencyInjector = new DependencyInjector();

    public static void main(String[] args) {
        try {
            Main.startup();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void startup() throws Exception {
        EnvConfigService configService = Main.dependencyInjector.getConfigService();
        Main.dependencyInjector.getUpdatePlatformsListUseCase().execute(configService.getCoresPath());
        Main.dependencyInjector.getUpdateGamesListUseCase().execute(configService.getRomsPath());
    }
}