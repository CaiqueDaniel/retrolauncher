package integration;

import fixtures.StubSettingRepository;
import org.junit.jupiter.api.*;
import org.retrolauncher.backend.app.platforms.application.exceptions.CoreFolderNotFoundException;
import org.retrolauncher.backend.app.platforms.application.services.PlatformsResourceConfigService;
import org.retrolauncher.backend.app.platforms.application.usecases.UpdatePlatformsListUseCase;
import org.retrolauncher.backend.app.platforms.domain.repositories.PlatformRepository;
import org.retrolauncher.backend.app.platforms.infrastructure.database.jackson.repositories.MemoryPlatformRepository;
import org.retrolauncher.backend.app.platforms.infrastructure.services.FilePlatformResourceConfigService;
import org.retrolauncher.backend.app.settings.domain.entities.Setting;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UpdatePlatformsListUseCaseIntegrationTests {
    private final String testFile = "./test-folder/cores/mesen.test";
    private final PlatformRepository repository = new MemoryPlatformRepository();
    private final StubSettingRepository settingRepository = new StubSettingRepository();
    private final PlatformsResourceConfigService resourceConfigService = new FilePlatformResourceConfigService();
    private final UpdatePlatformsListUseCase sut = new UpdatePlatformsListUseCase(
            repository,
            settingRepository,
            resourceConfigService
    );

    @BeforeAll
    void beforeAll() throws IOException {
        File folder = new File(testFile);
        folder.getParentFile().mkdirs();
        folder.createNewFile();
    }

    @BeforeEach
    void beforeEach() {
        repository.clear();
    }

    @AfterAll
    void afterAll() {
        File file = new File(testFile);
        file.delete();
        file.getParentFile().delete();
    }

    @Test()
    void it_should_be_able_to_save_cores_given_retroarch_folder() throws Exception {
        settingRepository.save(new Setting(Path.of("test-folder2"), Path.of("test-folder")));
        sut.execute();
        assertEquals(1, repository.listAll().size());
    }

    @Test()
    void it_should_be_able_to_save_cores_given_retroarch_cores_folder() throws Exception {
        settingRepository.save(new Setting(Path.of("test-folder2"), Path.of("test-folder/cores")));
        sut.execute();
        assertEquals(1, repository.listAll().size());
    }

    @Test
    void it_should_not_save_cores_given_folder_do_not_exists() {
        settingRepository.save(new Setting(Path.of("invalid-folder"), Path.of("test-folder2")));
        assertThrows(CoreFolderNotFoundException.class, sut::execute);
    }
}
