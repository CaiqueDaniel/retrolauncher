package org.retrolauncher.backend;

import org.retrolauncher.backend.config.CommandsHandler;
import org.retrolauncher.backend.config.DependencyInjector;
import org.retrolauncher.backend.events.DefaultEventManager;
import org.retrolauncher.backend.events.EventsRegister;

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
        EventsRegister eventsRegister = new EventsRegister(
                Backend.dependencyInjector,
                DefaultEventManager.getInstance()
        );
        eventsRegister.register();
    }
}
