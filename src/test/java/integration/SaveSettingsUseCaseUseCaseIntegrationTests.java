package integration;

import fixtures.StubSettingRepository;
import org.junit.jupiter.api.*;
import org.retrolauncher.backend.app._shared.application.exceptions.RetroarchNotFoundException;
import org.retrolauncher.backend.app._shared.application.services.EventDispatcherService;
import org.retrolauncher.backend.app.settings.application.dtos.SaveSettingsInputDto;
import org.retrolauncher.backend.app.settings.application.usecases.SaveSettingsUseCase;
import org.retrolauncher.backend.app.settings.domain.entities.Setting;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SaveSettingsUseCaseUseCaseIntegrationTests {
    private final StubSettingRepository repository = new StubSettingRepository();
    private final EventDispatcherService eventDispatcherService = mock(EventDispatcherService.class);
    private final SaveSettingsUseCase saveSettingsUseCase = new SaveSettingsUseCase(repository, eventDispatcherService);
    private final File testFolder = new File("test");

    @BeforeAll
    void beforeAll() {
        testFolder.mkdirs();
    }

    @AfterEach()
    void afterEach() {
        Arrays.stream(Objects.requireNonNull(testFolder.listFiles())).toList().forEach(File::delete);
    }

    @AfterAll
    void afterAll() {
        testFolder.delete();
    }

    @Test
    void it_should_be_able_to_save_settings() throws IOException {
        Path executablePath = Path.of(testFolder.getAbsoluteFile().getPath()).resolve("retroarch.exe");
        executablePath.toFile().createNewFile();

        SaveSettingsInputDto dto = new SaveSettingsInputDto(
                testFolder.getAbsolutePath(),
                testFolder.getAbsolutePath()
        );
        saveSettingsUseCase.execute(dto);
        Optional<Setting> result = repository.get();
        assertEquals(testFolder.getAbsolutePath(), result.get().getRomsFolderPath().toString());
        assertEquals(testFolder.getAbsolutePath(), result.get().getRetroarchFolderPath().toString());
    }

    @Test
    void it_should_not_save_setting_given_retroarch_folder_does_not_have_executable() {
        SaveSettingsInputDto dto = new SaveSettingsInputDto(testFolder.getAbsolutePath(), testFolder.getAbsolutePath());
        assertThrows(RetroarchNotFoundException.class, () -> saveSettingsUseCase.execute(dto));
    }
}
