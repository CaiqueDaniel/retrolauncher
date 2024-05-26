package org.retrolauncher.gui.modules.settings.models;

import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;

@Getter
@Setter
public class Settings {
    private Path romPath;
    private Path retroarchPath;

    public Settings(Path romPath, Path retroarchPath) {
        this.romPath = romPath;
        this.retroarchPath = retroarchPath;
    }
}