package org.retrolauncher.backend.app.settings.domain.entities;

import lombok.Getter;
import org.retrolauncher.backend.app.settings.domain.exceptions.InvalidROMPathException;
import org.retrolauncher.backend.app.settings.domain.exceptions.InvalidRetroarchPathException;

import java.io.File;
import java.nio.file.Path;

@Getter
public class Setting {
    private final Path romsFolderPath;
    private final Path retroarchFolderPath;

    public Setting(Path romsFolderPath, Path retroarchFolderPath) {
        this.romsFolderPath = romsFolderPath.toAbsolutePath();
        this.retroarchFolderPath = retroarchFolderPath.toAbsolutePath();
        this.validate();
    }

    public Setting(String romsFolderPath, String retroarchFolderPath) {
        this.romsFolderPath = Path.of(romsFolderPath);
        this.retroarchFolderPath = Path.of(retroarchFolderPath);
        this.validate();
    }

    private void validate() {
        if (isInvalidPath(romsFolderPath))
            throw new InvalidROMPathException();
        if (isInvalidPath(retroarchFolderPath))
            throw new InvalidRetroarchPathException();
    }

    private boolean isInvalidPath(Path path) {
        File file = path.toFile();
        return !file.isDirectory() || !file.exists() || !file.canRead();
    }
}
