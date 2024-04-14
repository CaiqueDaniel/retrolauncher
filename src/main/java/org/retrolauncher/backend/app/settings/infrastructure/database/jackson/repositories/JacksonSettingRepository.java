package org.retrolauncher.backend.app.settings.infrastructure.database.jackson.repositories;

import org.retrolauncher.backend.app.settings.domain.entities.Setting;
import org.retrolauncher.backend.app.settings.domain.repositories.SettingRepository;
import org.retrolauncher.backend.app.settings.infrastructure.database.jackson.mappers.JacksonSettingMapper;
import org.retrolauncher.backend.app.settings.infrastructure.database.jackson.model.SettingModel;
import org.retrolauncher.backend.database.FileDatabaseDriver;

import java.util.Map;
import java.util.Optional;

public class JacksonSettingRepository implements SettingRepository {
    private final FileDatabaseDriver<Map<String, SettingModel>> driver;
    private static final String FILE_PATH = new StringBuilder(System.getProperty("user.home"))
            .append("/retro-launcher")
            .append("/data")
            .append("/setting.json")
            .toString();

    public JacksonSettingRepository(FileDatabaseDriver<Map<String, SettingModel>> driver) {
        this.driver = driver;
    }

    public void save(Setting entity) {
        try {
            Map<String, SettingModel> storedData = this.driver.read(FILE_PATH);
            storedData.put("setup", JacksonSettingMapper.fromDomain(entity));
            this.driver.write(storedData, FILE_PATH);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Optional<Setting> get() {
        try {
            Map<String, SettingModel> storedData = this.driver.read(FILE_PATH);
            SettingModel model = storedData.get("setup");

            if (model == null)
                return Optional.empty();

            return Optional.of(JacksonSettingMapper.toDomain(model));
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
