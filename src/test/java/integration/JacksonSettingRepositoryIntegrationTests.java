package integration;

import org.junit.jupiter.api.*;
import org.retrolauncher.backend.app.settings.domain.entities.Setting;
import org.retrolauncher.backend.app.settings.infrastructure.database.jackson.model.SettingModel;
import org.retrolauncher.backend.app.settings.infrastructure.database.jackson.repositories.JacksonSettingRepository;
import org.retrolauncher.backend.database.FileDatabaseDriver;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JacksonSettingRepositoryIntegrationTests {
    private JacksonSettingRepository sut;
    private Setting setting;
    private final StubFileDatabaseDriver driver = new StubFileDatabaseDriver();

    private final File testFolder = new File("test");

    @BeforeAll
    void setup() {
        testFolder.mkdirs();
        sut = new JacksonSettingRepository(driver);
    }

    @BeforeEach
    void beforeEach() {
        setting = new Setting(testFolder.toPath(), testFolder.toPath());
    }

    @AfterEach
    void destroy() {
        driver.clear("");
    }

    @AfterAll
    void afterAll() {
        testFolder.delete();
    }

    @Test
    void it_should_be_able_to_save() {
        sut.save(setting);
        assertTrue(sut.get().isPresent());
    }
}

class StubFileDatabaseDriver implements FileDatabaseDriver<Map<String, SettingModel>> {
    private Map<String, SettingModel> storedData = new HashMap<>();

    @Override
    public void write(Map<String, SettingModel> content, String path) {
        this.storedData = content;
    }


    @Override
    public Map<String, SettingModel> read(String path) {
        return this.storedData;
    }

    @Override
    public void clear(String path) {
        this.storedData.clear();
    }
}