package org.retrolauncher.backend.app.games.application.factories;

import org.retrolauncher.backend.app.games.application.services.GameFinderService;

public interface GameFinderFactory {
    GameFinderService createFrom(String platformName);
}
