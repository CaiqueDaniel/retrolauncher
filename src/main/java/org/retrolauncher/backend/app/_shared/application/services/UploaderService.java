package org.retrolauncher.backend.app._shared.application.services;

import java.io.File;

public interface UploaderService {
    String upload(File file);

    String upload(File file, String filename);
}
