package org.retrolauncher.backend.app.games.application.factories;

import org.retrolauncher.backend.app.games.application.services.PlatformDetectorService;

public interface PlatformDetectorFactory {
    PlatformDetectorService createFrom(String name);
}
