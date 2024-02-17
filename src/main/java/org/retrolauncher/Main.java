package org.retrolauncher;

import org.retrolauncher.app._shared.application.services.EnvConfigService;
import org.retrolauncher.config.CommandsHandler;

public class Main {
    private static final DependencyInjector dependencyInjector = new DependencyInjector();

    public static void main(String[] args) {
        try {
            Main.startup();
            Main.registerCommands(args);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void startup() throws Exception {
        EnvConfigService configService = Main.dependencyInjector.getConfigService();
        Main.dependencyInjector.getUpdatePlatformsListUseCase().execute(configService.getCoresPath());
        Main.dependencyInjector.getUpdateGamesListUseCase().execute(configService.getRomsPath());
    }

    private static void registerCommands(String[] args) {
        new CommandsHandler(Main.dependencyInjector, args).run();
    }
}