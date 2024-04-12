package org.retrolauncher.backend;

import org.retrolauncher.backend.app._shared.application.services.EnvConfigService;
import org.retrolauncher.backend.config.CommandsHandler;
import org.retrolauncher.backend.config.DependencyInjector;

public class Backend {
    private static final DependencyInjector dependencyInjector = new DependencyInjector();

    public static void main(String[] args) {
        try {
            Backend.startup();
            Backend.registerCommands(args);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static DependencyInjector getDependencies() {
        return Backend.dependencyInjector;
    }

    private static void startup() throws Exception {
        EnvConfigService configService = Backend.dependencyInjector.getConfigService();
        Backend.dependencyInjector.getUpdatePlatformsListUseCase().execute(configService.getCoresPath());
        Backend.dependencyInjector.getUpdateGamesListUseCase().execute();
    }

    private static void registerCommands(String[] args) {
        new CommandsHandler(Backend.dependencyInjector, args).run();
    }
}
