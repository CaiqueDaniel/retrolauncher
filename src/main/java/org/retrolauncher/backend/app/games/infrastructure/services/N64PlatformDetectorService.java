package org.retrolauncher.backend.app.games.infrastructure.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class N64PlatformDetectorService extends ExtensionReaderPlatformDetectorService {
    public N64PlatformDetectorService() {
        super(new String[]{"n64", "z64"});
    }

    @Override
    public boolean isFromPlatform(File file) {
        List<String> extensions = new ArrayList<>(List.of(new String[]{"z64", "n64"}));
        return extensions.stream().anyMatch((extension) -> file.getName().contains(extension));
    }
}
