package org.retrolauncher.backend.app.games.infrastructure.factories;

import org.retrolauncher.backend.app.games.application.factories.PlatformDetectorFactory;
import org.retrolauncher.backend.app.games.application.services.PlatformDetectorService;
import org.retrolauncher.backend.app.games.infrastructure.services.*;

public class LocalPlatformDetectorFactory implements PlatformDetectorFactory {
    @Override
    public PlatformDetectorService createFrom(String name) {
        return switch (name) {
            case "Nintendo Entertainment System" -> new NESPlatformDetectorService();
            case "Super Nintendo Entertainment System" -> new SNESPlatformDetectorService();
            case "Game Boy/Color" -> new GBCPlatformDetectorService();
            case "Sega Master System" -> new SMSPlatformDetectorService();
            case "Playstation 1" -> new PSXPlatformDetectorService();
            case "Nintendo 64" -> new N64PlatformDetectorService();
            default -> throw new RuntimeException();
        };
    }
}
