package org.retrolauncher.backend.events;

import java.util.*;
import java.util.function.Consumer;

public class DefaultEventManager implements EventManager {
    private final Map<String, List<Consumer<Optional<Object>>>> eventListeners;
    private static DefaultEventManager instance;

    private DefaultEventManager() {
        this.eventListeners = new HashMap<>();
    }

    public void subscribe(String eventType, Consumer<Optional<Object>> listener) {
        this.eventListeners.computeIfAbsent(eventType, k -> new ArrayList<>());
        this.eventListeners.get(eventType).add(listener);
    }

    public void notify(String eventType) {
        this.notify(eventType, Optional.empty());
    }

    public void notify(String eventType, Object data) {
        this.eventListeners.get(eventType).forEach((listener) -> listener.accept(Optional.of(data)));
    }

    public static DefaultEventManager getInstance() {
        if (DefaultEventManager.instance == null)
            DefaultEventManager.instance = new DefaultEventManager();
        return DefaultEventManager.instance;
    }
}

