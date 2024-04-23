package unit;

import org.junit.jupiter.api.*;
import org.retrolauncher.backend.app.settings.domain.entities.Setting;
import org.retrolauncher.backend.app.settings.infrastructure.database.jackson.mappers.JacksonSettingMapper;
import org.retrolauncher.backend.app.settings.infrastructure.database.jackson.model.SettingModel;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JacksonSettingMapperUnitTests {
    private final File testFolder = new File("test");

    @BeforeAll
    void beforeAll() {
        testFolder.mkdirs();
    }

    @AfterAll
    void afterAll() {
        testFolder.delete();
    }

    @Test
    void itShouldBeAbleToCreateAModel() {
        Setting entity = new Setting(testFolder.toPath(), testFolder.toPath());
        SettingModel result = JacksonSettingMapper.fromDomain(entity);

        assertEquals(result.getRomsFolderPath(), entity.getRomsFolderPath().toString());
        assertEquals(result.getRetroarchFolderPath(), entity.getRetroarchFolderPath().toString());
    }

    @Test
    void itShouldBeAbleToCreateAEntity() {
        Setting entity = new Setting(testFolder.toPath(), testFolder.toPath());
        SettingModel model = JacksonSettingMapper.fromDomain(entity);
        Setting result = JacksonSettingMapper.toDomain(model);

        assertTrue(result.getRomsFolderPath().toString().equals(entity.getRomsFolderPath().toString()));
        assertTrue(result.getRetroarchFolderPath().toString().equals(entity.getRetroarchFolderPath().toString()));
    }
}
