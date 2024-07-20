package org.retrolauncher.backend.app.games.infrastructure.services;

public class SNESPlatformDetectorService extends ExtensionReaderPlatformDetectorService {
    public SNESPlatformDetectorService() {
        super(new String[]{"sfc"});
    }
}
