package org.retrolauncher.backend.app._shared.application.services;

import org.retrolauncher.backend.app._shared.application.dtos.EventType;

public interface EventDispatcherService {
    void dispatch(EventType eventType);

    void dispatch(EventType eventType, Object data);
}
