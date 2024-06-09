package org.retrolauncher.backend.app.settings.domain.entities;

import lombok.Getter;
import org.retrolauncher.backend.app.settings.domain.exceptions.SettingValidationException;
import org.retrolauncher.backend.app.settings.domain.validators.SettingValidator;

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
        final SettingValidator validator = new SettingValidator(this);
        if (validator.hasErrors())
            throw new SettingValidationException(validator.getErrors());
    }
}
