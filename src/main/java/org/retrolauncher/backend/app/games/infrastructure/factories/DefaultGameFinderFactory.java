package org.retrolauncher.backend.app.games.infrastructure.factories;

import org.retrolauncher.backend.app.games.application.factories.GameFinderFactory;
import org.retrolauncher.backend.app.games.application.services.GameFinderService;
import org.retrolauncher.backend.app.games.infrastructure.services.*;

public class DefaultGameFinderFactory implements GameFinderFactory {
    @Override
    public GameFinderService createFrom(String platformName) {
        return switch (platformName) {
            case "Nintendo Entertainment System" -> new NESGameFinderService();
            case "Super Nintendo Entertainment System" -> new SNESGameFinderService();
            case "Game Boy/Color" -> new GBGameFinderService();
            case "Sega Master System" -> new SMSGameFinderService();
            case "Playstation 1" -> new PSXGameFinderService();
            case "Nintendo 64" -> new N64GameFinderService();
            default -> throw new RuntimeException();
        };
    }
}
