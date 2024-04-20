package org.retrolauncher.backend.app._shared.application.exceptions;

import java.io.IOException;
import java.nio.file.Path;

public class FileCouldNotBeDeletedException extends IOException {
    public FileCouldNotBeDeletedException(Path path) {
        super("File " + path.toAbsolutePath() + " could not be deleted");
    }
}
