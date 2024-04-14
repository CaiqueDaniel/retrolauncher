package org.retrolauncher.backend;

import org.retrolauncher.backend.config.CommandsHandler;
import org.retrolauncher.backend.config.DependencyInjector;
import org.retrolauncher.backend.events.DefaultEventManager;
import org.retrolauncher.backend.app._shared.application.dtos.EventType;

import java.io.FileNotFoundException;

public class Backend {
    private static final DependencyInjector dependencyInjector = new DependencyInjector();

    public static void main(String[] args) {
        try {
            Backend.registerEvents();
            Backend.registerCommands(args);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static DependencyInjector getDependencies() {
        return Backend.dependencyInjector;
    }

    private static void registerCommands(String[] args) {
        new CommandsHandler(Backend.dependencyInjector, args).run();
    }

    private static void registerEvents() {
        System.out.println("ldçfjklfçkslçdkflç");
        DefaultEventManager.getInstance().subscribe(EventType.SETTINGS_UPDATED.name(), (data) -> {
            try {
                //dependencyInjector.getUpdatePlatformsListUseCase().execute();
                dependencyInjector.getUpdateGamesListUseCase().execute();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
