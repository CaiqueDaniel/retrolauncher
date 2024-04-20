package org.retrolauncher.backend.app.games.application.services;

import org.retrolauncher.backend.app._shared.application.exceptions.FileCouldNotBeDeletedException;
import org.retrolauncher.backend.app._shared.infrastructure.services.SystemFileManagerService;

import java.nio.file.Path;

public class CoverFileManagerService extends SystemFileManagerService {
    @Override
    public void delete(Path coverPath) throws FileCouldNotBeDeletedException {
        String coverExtension = getExtension(coverPath.toFile().getName());
        Path iconPath = Path.of(coverPath.toAbsolutePath().toString().replace(coverExtension, ".ico"));

        super.delete(coverPath);
        super.delete(iconPath);
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.'));
    }
}
