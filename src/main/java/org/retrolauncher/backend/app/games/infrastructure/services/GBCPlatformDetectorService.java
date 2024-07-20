package org.retrolauncher.backend.app.games.infrastructure.services;

public class GBCPlatformDetectorService extends ExtensionReaderPlatformDetectorService {
    public GBCPlatformDetectorService() {
        super(new String[]{"gb", "gbc"});
    }
}
