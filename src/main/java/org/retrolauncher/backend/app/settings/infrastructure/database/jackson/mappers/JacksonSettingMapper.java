package org.retrolauncher.backend.app.settings.infrastructure.database.jackson.mappers;

import org.retrolauncher.backend.app.settings.domain.entities.Setting;
import org.retrolauncher.backend.app.settings.infrastructure.database.jackson.model.SettingModel;

import java.nio.file.Path;

public class JacksonSettingMapper {
    private JacksonSettingMapper() {
    }

    public static SettingModel fromDomain(Setting entity) {
        SettingModel model = new SettingModel();

        model.setRomsFolderPath(entity.getRomsFolderPath().toString());
        model.setRetroarchFolderPath(entity.getRetroarchFolderPath().toString());

        return model;
    }

    public static Setting toDomain(SettingModel model) {
        return new Setting(Path.of(model.getRomsFolderPath()), Path.of(model.getRetroarchFolderPath()));
    }
}
