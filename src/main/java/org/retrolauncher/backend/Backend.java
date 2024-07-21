package org.retrolauncher.backend;

import org.retrolauncher.Main;
import org.retrolauncher.backend.config.CommandsHandler;
import org.retrolauncher.backend.config.DependencyInjector;
import org.retrolauncher.backend.events.DefaultEventManager;
import org.retrolauncher.backend.events.EventsRegister;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Backend {
    private static final DependencyInjector dependencyInjector;

    static {
        try {
            dependencyInjector = new DependencyInjector();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {
            Backend.registerEvents();
            Backend.registerCommands(args);
            getDependencies().getPlatformController().updateList();
        } catch (Exception exception) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, exception.getMessage());
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
