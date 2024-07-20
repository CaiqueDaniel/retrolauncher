package org.retrolauncher.backend.app.games.infrastructure.services;

public class N64PlatformDetectorService extends ExtensionReaderPlatformDetectorService {
    public N64PlatformDetectorService() {
        super(new String[]{"n64", "z64"});
    }
}
