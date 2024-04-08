package org.retrolauncher.backend.app.settings.infrastructure.database.jackson.repositories;

import org.retrolauncher.backend.app.settings.domain.entities.Setting;
import org.retrolauncher.backend.app.settings.domain.repositories.SettingRepository;
import org.retrolauncher.backend.app.settings.infrastructure.database.jackson.mappers.JacksonSettingMapper;
import org.retrolauncher.backend.app.settings.infrastructure.database.jackson.model.SettingModel;
import org.retrolauncher.backend.database.FileDatabaseDriver;

import java.util.Optional;

public class JacksonSettingRepository implements SettingRepository {
    private final FileDatabaseDriver<SettingModel> driver;
    private static final String FILE_PATH = new StringBuilder(System.getProperty("user.home"))
            .append("/retro-launcher")
            .append("/data")
            .append("/setting.json")
            .toString();

    public JacksonSettingRepository(FileDatabaseDriver<SettingModel> driver) {
        this.driver = driver;
    }

    @Override
    public void save(Setting entity) {
        try {
            this.driver.write(JacksonSettingMapper.fromDomain(entity), FILE_PATH);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Optional<Setting> get() {
        try {
            SettingModel model = this.driver.read(FILE_PATH);

            if (model == null)
                return Optional.empty();

            return Optional.of(JacksonSettingMapper.toDomain(model));
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
