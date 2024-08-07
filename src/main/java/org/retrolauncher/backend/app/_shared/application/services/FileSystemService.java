package org.retrolauncher.backend.app._shared.application.services;

import java.io.File;
import java.nio.file.Path;

public interface FileSystemService {
    Path upload(File file);

    Path upload(File file, String filename);

    void remove(Path path);
}
