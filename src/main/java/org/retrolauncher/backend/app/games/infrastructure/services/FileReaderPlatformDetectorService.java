package org.retrolauncher.backend.app.games.infrastructure.services;

import org.retrolauncher.backend.app.games.application.services.PlatformDetectorService;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class FileReaderPlatformDetectorService implements PlatformDetectorService {
    private final String identifier;

    public FileReaderPlatformDetectorService(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public boolean isFromPlatform(File file) {
        String line;
        FileInputStream inputStream = null;
        boolean result = false;

        try {
            inputStream = new FileInputStream(file);
            final BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));

            while ((line = buffer.readLine()) != null) {
                result = line.contains(identifier);
                if (result) break;
            }
        } catch (IOException exception) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, exception.getMessage());
        }

        closeInputStream(inputStream);

        return result;
    }

    private void closeInputStream(InputStream inputStream) {
        if (inputStream == null)
            return;

        try {
            inputStream.close();
        } catch (IOException exception) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, exception.getMessage());
        }
    }
}
