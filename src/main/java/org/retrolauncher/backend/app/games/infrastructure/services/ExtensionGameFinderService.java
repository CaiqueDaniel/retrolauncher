package org.retrolauncher.backend.app.games.infrastructure.services;

import org.retrolauncher.backend.app.games.application.services.GameFinderService;

import java.io.File;
import java.util.*;

public abstract class ExtensionGameFinderService implements GameFinderService {
    private final List<String> extensions = new ArrayList<>();

    public ExtensionGameFinderService(String[] extensions) {
        this.extensions.addAll(List.of(extensions));
    }

    public List<File> getFilesFrom(File folder) {
        final var files = folder.listFiles();
        final var identifiedFiles = new ArrayList<File>();

        if (files == null) return identifiedFiles;

        for (var file : files) {
            if (!isFileWithinAllowedExtensions(file)) continue;
            identifiedFiles.add(file);
        }

        return identifiedFiles;
    }

    private boolean isFileWithinAllowedExtensions(File file) {
        return extensions.stream().anyMatch((extension) -> file.getName().contains(extension));
    }
}
