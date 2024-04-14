package org.retrolauncher.backend.app._shared.infrastructure.services;

import org.retrolauncher.backend.app._shared.application.dtos.EventType;
import org.retrolauncher.backend.app._shared.application.services.EventDispatcherService;
import org.retrolauncher.backend.events.EventManager;

public class DefaultEventDispatcherService implements EventDispatcherService {
    private final EventManager manager;

    public DefaultEventDispatcherService(EventManager manager) {
        this.manager = manager;
    }

    @Override
    public void dispatch(EventType eventType) {
        this.manager.notify(eventType.name());
    }

    @Override
    public void dispatch(EventType eventType, Object data) {
        this.manager.notify(eventType.name(), data);
    }
}
