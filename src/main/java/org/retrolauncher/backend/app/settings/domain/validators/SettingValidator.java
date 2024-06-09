package org.retrolauncher.backend.app.settings.domain.validators;

import org.retrolauncher.backend.app._shared.domain.validators.Validator;
import org.retrolauncher.backend.app.settings.domain.entities.Setting;

import java.io.File;
import java.nio.file.Path;

public class SettingValidator extends Validator {
    private final Setting setting;

    public SettingValidator(Setting setting) {
        this.setting = setting;
    }

    public boolean hasErrors() {
        validateRomsPath();
        validateRetroarchPath();
        return !errors.isEmpty();
    }

    private void validateRomsPath() {
        if (isInvalidPath(setting.getRomsFolderPath()))
            errors.put("romsFolderPath", "ROM's folder path is invalid");
    }

    private void validateRetroarchPath() {
        if (isInvalidPath(setting.getRetroarchFolderPath()))
            errors.put("retroarchFolderPath", "Retroarch folder does not exists");
    }

    private boolean isInvalidPath(Path path) {
        final File file = path.toFile();
        return !file.isDirectory() || !file.exists() || !file.canRead();
    }
}
