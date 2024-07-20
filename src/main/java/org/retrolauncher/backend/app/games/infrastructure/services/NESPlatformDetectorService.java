package org.retrolauncher.backend.app.games.infrastructure.services;

public class NESPlatformDetectorService extends ExtensionReaderPlatformDetectorService {
    public NESPlatformDetectorService() {
        super(new String[]{"nes", "fds"});
    }
}
