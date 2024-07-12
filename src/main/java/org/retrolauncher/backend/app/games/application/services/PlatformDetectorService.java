package org.retrolauncher.backend.app.games.application.services;

import java.io.File;

public interface PlatformDetectorService {
    boolean isFromPlatform(File file);
}
