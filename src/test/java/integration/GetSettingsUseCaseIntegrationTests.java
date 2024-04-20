package integration;

import fixtures.StubSettingRepository;
import org.junit.jupiter.api.*;
import org.retrolauncher.backend.app.settings.application.dtos.GetSettingsOutputDto;
import org.retrolauncher.backend.app.settings.application.exceptions.SettingNotFoundException;
import org.retrolauncher.backend.app.settings.application.usecases.GetSettingsUseCase;
import org.retrolauncher.backend.app.settings.domain.entities.Setting;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetSettingsUseCaseIntegrationTests {
    private final StubSettingRepository repository = new StubSettingRepository();
    private final GetSettingsUseCase sut = new GetSettingsUseCase(repository);

    private final File testFolder = new File("test");

    @BeforeAll
    void beforeAll() {
        testFolder.mkdirs();
    }

    @AfterEach
    void afterEach() {
        repository.clear();
        Arrays.stream(Objects.requireNonNull(testFolder.listFiles())).toList().forEach(File::delete);
    }

    @AfterAll
    void afterAll() {
        testFolder.delete();
    }

    @Test
    void it_should_be_able_to_get_settings() {
        repository.save(new Setting(testFolder.getAbsolutePath(), testFolder.getAbsolutePath()));
        GetSettingsOutputDto result = sut.execute();
        assertEquals(testFolder.getAbsolutePath(), result.romsFolderPath());
        assertEquals(testFolder.getAbsolutePath(), result.retroarchFolderPath());
    }

    @Test
    void it_should_not_be_able_to_get_settings_given_there_is_none() {
        assertThrows(SettingNotFoundException.class, sut::execute);
    }
}