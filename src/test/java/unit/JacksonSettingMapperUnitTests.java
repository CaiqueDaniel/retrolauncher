package unit;

import org.junit.jupiter.api.Test;
import org.retrolauncher.backend.app.settings.domain.entities.Setting;
import org.retrolauncher.backend.app.settings.infrastructure.database.jackson.mappers.JacksonSettingMapper;
import org.retrolauncher.backend.app.settings.infrastructure.database.jackson.model.SettingModel;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class JacksonSettingMapperUnitTests {
    @Test
    void itShouldBeAbleToCreateAModel() {
        Setting entity = new Setting(Path.of("C:/roms"), Path.of("C:/retroarch"));
        SettingModel result = JacksonSettingMapper.fromDomain(entity);

        assertEquals(result.getRomsFolderPath(), entity.getRomsFolderPath().toString());
        assertEquals(result.getRetroarchFolderPath(), entity.getRetroarchFolderPath().toString());
    }

    @Test
    void itShouldBeAbleToCreateAEntity() {
        Setting entity = new Setting(Path.of("C:/roms"), Path.of("C:/retroarch"));
        SettingModel model = JacksonSettingMapper.fromDomain(entity);
        Setting result = JacksonSettingMapper.toDomain(model);

        assertTrue(result.getRomsFolderPath().toString().equals(entity.getRomsFolderPath().toString()));
        assertTrue(result.getRetroarchFolderPath().toString().equals(entity.getRetroarchFolderPath().toString()));
    }
}
