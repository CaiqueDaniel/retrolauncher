package org.retrolauncher.backend.app.games.infrastructure.services;

import org.retrolauncher.backend.app.games.application.services.PlatformDetectorService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class ExtensionReaderPlatformDetectorService implements PlatformDetectorService {
    private final List<String> extensions = new ArrayList<>();

    public ExtensionReaderPlatformDetectorService(String[] extensions) {
        this.extensions.addAll(List.of(extensions));
    }

    @Override
    public boolean isFromPlatform(File file) {
        return extensions.stream().anyMatch((extension) -> file.getName().contains(extension));
    }
}
