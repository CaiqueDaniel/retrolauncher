package org.retrolauncher.backend.app._shared.application.services;

import org.retrolauncher.backend.app._shared.application.exceptions.FileCouldNotBeDeletedException;

import java.nio.file.Path;

public interface FileManagerService {
    void delete(Path path) throws FileCouldNotBeDeletedException;
}
