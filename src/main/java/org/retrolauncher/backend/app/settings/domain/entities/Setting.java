package org.retrolauncher.backend.app.settings.domain.entities;

import lombok.Getter;
import org.retrolauncher.backend.app.settings.dtos.SaveSettingsInputDto;

import java.nio.file.Path;

@Getter
public class Setting {
    private final Path romsFolderPath;
    private final Path retroarchFolderPath;

    public Setting(Path romsFolderPath, Path retroarchFolderPath) {
        this.romsFolderPath = romsFolderPath;
        this.retroarchFolderPath = retroarchFolderPath;
    }

    public Setting(SaveSettingsInputDto dto) {
        this.romsFolderPath = Path.of(dto.romsFolderPath());
        this.retroarchFolderPath = Path.of(dto.retroarchFolderPath());
    }
}
