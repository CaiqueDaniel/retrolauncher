package org.retrolauncher.backend.app._shared.infrastructure.services;

import org.retrolauncher.backend.app._shared.application.exceptions.FileCouldNotBeDeletedException;
import org.retrolauncher.backend.app._shared.application.services.FileManagerService;

import java.io.File;
import java.nio.file.Path;

public class SystemFileManagerService implements FileManagerService {
    @Override
    public void delete(Path path) throws FileCouldNotBeDeletedException {
        File file = path.toFile();

        if (!file.exists()) return;
        if (!file.delete()) throw new FileCouldNotBeDeletedException(path);
    }
}
