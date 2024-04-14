package org.retrolauncher.backend.app.settings.domain.entities;

import lombok.Getter;
import java.nio.file.Path;

@Getter
public class Setting {
    private final Path romsFolderPath;
    private final Path retroarchFolderPath;

    public Setting(Path romsFolderPath, Path retroarchFolderPath) {
        this.romsFolderPath = romsFolderPath;
        this.retroarchFolderPath = retroarchFolderPath;
    }

    public Setting(String romsFolderPath, String retroarchFolderPath) {
        this.romsFolderPath = Path.of(romsFolderPath);
        this.retroarchFolderPath = Path.of(retroarchFolderPath);
    }
}
