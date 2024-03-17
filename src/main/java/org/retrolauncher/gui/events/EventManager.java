package org.retrolauncher.gui.events;

import java.util.*;
import java.util.function.Consumer;

public class EventManager {
    private final Map<EventType, List<Consumer<Optional<Object>>>> eventListeners;
    private static EventManager instance;

    private EventManager() {
        this.eventListeners = new HashMap<>();
    }

    public void subscribe(EventType eventType, Consumer<Optional<Object>> listener) {
        this.eventListeners.computeIfAbsent(eventType, k -> new ArrayList<>());
        this.eventListeners.get(eventType).add(listener);
    }

    public void notify(EventType eventType) {
        this.notify(eventType, Optional.empty());
    }

    public void notify(EventType eventType, Object data) {
        this.eventListeners.get(eventType).forEach((listener) -> listener.accept(Optional.of(data)));
    }

    public static EventManager getInstance() {
        if (EventManager.instance == null)
            EventManager.instance = new EventManager();
        return EventManager.instance;
    }
}
