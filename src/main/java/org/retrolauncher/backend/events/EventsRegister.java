package org.retrolauncher.backend.events;

import org.retrolauncher.backend.app._shared.application.dtos.EventType;
import org.retrolauncher.backend.config.DependencyInjector;

import java.io.FileNotFoundException;

public class EventsRegister {
    private final DependencyInjector dependencyInjector;
    private final EventManager eventManager;

    public EventsRegister(DependencyInjector dependencyInjector, EventManager eventManager) {
        this.dependencyInjector = dependencyInjector;
        this.eventManager = eventManager;
    }

    public void register() {
        eventManager.subscribe(EventType.ROMS_FOLDER_UPDATED.name(), (data) -> {
            try {
                dependencyInjector.getUpdateGamesListUseCase().execute();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
