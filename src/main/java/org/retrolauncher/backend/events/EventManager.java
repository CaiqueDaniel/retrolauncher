package org.retrolauncher.backend.events;

import java.util.Optional;
import java.util.function.Consumer;

public interface EventManager {
    void subscribe(String eventType, Consumer<Optional<Object>> listener);

    void notify(String eventType);

    void notify(String eventType, Object data);
}
