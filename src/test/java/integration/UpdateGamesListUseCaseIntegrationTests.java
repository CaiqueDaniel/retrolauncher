package integration;

import fixtures.StubSettingRepository;
import org.junit.jupiter.api.*;
import org.retrolauncher.backend.app._shared.application.services.FileManagerService;
import org.retrolauncher.backend.app.games.application.factories.PlatformDetectorFactory;
import org.retrolauncher.backend.app.games.application.services.PlatformDetectorService;
import org.retrolauncher.backend.app.games.domain.entities.Game;
import org.retrolauncher.backend.app.games.infrastructure.database.memory.MemoryGameRepository;
import org.retrolauncher.backend.app.games.application.usecases.UpdateGamesListUseCase;
import org.retrolauncher.backend.app.platforms.domain.entities.Platform;
import org.retrolauncher.backend.app.platforms.domain.repositories.PlatformRepository;
import org.retrolauncher.backend.app.platforms.infrastructure.database.memory.MemoryPlatformRepository;
import org.retrolauncher.backend.app.settings.application.exceptions.SettingNotFoundException;
import org.retrolauncher.backend.app.settings.domain.entities.Setting;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UpdateGamesListUseCaseIntegrationTests {
    private final File testFolder = new File("test");
    private final PlatformRepository platformRepository = new MemoryPlatformRepository();
    private final MemoryGameRepository repository = new MemoryGameRepository(this.platformRepository);
    private final StubSettingRepository settingRepository = new StubSettingRepository();
    private final FileManagerService mockedFileManagerService = mock(FileManagerService.class);
    private final UpdateGamesListUseCase sut = new UpdateGamesListUseCase(
            repository,
            platformRepository,
            settingRepository,
            mockedFileManagerService,
            new StubPlatformDetectorFactory()
    );

    @BeforeAll
    void beforeAll() {
        platformRepository.save(new Platform("Test", "test", List.of("test")));
        testFolder.mkdirs();
    }

    @BeforeEach
    void beforeEach() {
        testFolder.mkdirs();
    }

    @AfterEach
    void afterEach() {
        repository.clear();
        settingRepository.clear();
        Arrays.stream(Objects.requireNonNull(testFolder.listFiles())).toList().forEach(File::delete);
    }

    @AfterAll
    void afterAll() {
        testFolder.delete();
    }

    @Test
    void it_should_be_able_to_save_games() throws Exception {
        Path filePath = Path.of(testFolder.getAbsoluteFile().getPath()).resolve("game.test");
        filePath.toFile().createNewFile();
        settingRepository.save(new Setting(Path.of("test"), Path.of("test")));
        sut.execute();
        Game result = repository.listAll().get(0);
        assertEquals("game", result.getName());
        assertEquals(Optional.empty(), result.getIconPath());
        assertEquals(1, repository.listAll().size());
    }

    @Test
    void it_should_remove_games_that_not_longer_exists() throws IOException {
        Path filePath1 = Path.of(testFolder.getAbsoluteFile().getPath()).resolve("game1.test");
        filePath1.toFile().createNewFile();

        Path.of(testFolder.getAbsoluteFile().getPath())
                .resolve("game2.test")
                .toFile()
                .createNewFile();

        settingRepository.save(new Setting(testFolder.toPath(), testFolder.toPath()));
        sut.execute();

        assertEquals(2, repository.listAll().size());

        filePath1.toFile().delete();
        sut.execute();

        assertEquals(1, repository.listAll().size());
        assertEquals("game2", repository.listAll().get(0).getName());
    }

    @Test
    void it_should_not_save_games_given_that_there_is_no_settings() {
        assertThrows(SettingNotFoundException.class, sut::execute);
    }

    @Test
    void it_should_not_duplicate_games() throws IOException {
        Path filePath = Path.of(testFolder.getAbsoluteFile().getPath()).resolve("game.test");
        filePath.toFile().createNewFile();
        settingRepository.save(new Setting(Path.of("test"), Path.of("test")));
        sut.execute();
        sut.execute();
        assertEquals(1, repository.listAll().size());
    }
}

class StubPlatformDetectorFactory implements PlatformDetectorFactory {

    @Override
    public PlatformDetectorService createFrom(String name) {
        return file -> true;
    }
}