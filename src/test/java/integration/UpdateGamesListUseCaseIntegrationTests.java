package integration;

import fixtures.StubSettingRepository;
import org.junit.jupiter.api.*;
import org.retrolauncher.backend.app.games.domain.entities.Game;
import org.retrolauncher.backend.app.games.domain.repositories.GameRepository;
import org.retrolauncher.backend.app.games.infrastructure.database.jackson.repositories.MemoryGameRepository;
import org.retrolauncher.backend.app.games.application.usecases.UpdateGamesListUseCase;
import org.retrolauncher.backend.app.platforms.domain.entities.Platform;
import org.retrolauncher.backend.app.platforms.domain.repositories.PlatformRepository;
import org.retrolauncher.backend.app.platforms.infrastructure.database.jackson.repositories.MemoryPlatformRepository;
import org.retrolauncher.backend.app.settings.application.exceptions.SettingNotFoundException;
import org.retrolauncher.backend.app.settings.domain.entities.Setting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UpdateGamesListUseCaseIntegrationTests {
    private final String testFile = "test-folder/game.test";
    private final PlatformRepository platformRepository = new MemoryPlatformRepository();
    private final GameRepository repository = new MemoryGameRepository(this.platformRepository);
    private final StubSettingRepository settingRepository = new StubSettingRepository();
    private final UpdateGamesListUseCase sut = new UpdateGamesListUseCase(
            repository,
            this.platformRepository,
            this.settingRepository
    );

    @BeforeAll
    void beforeAll() throws IOException {
        platformRepository.save(new Platform("Test", "test", List.of("test")));
        File folder = new File(testFile);
        folder.getParentFile().mkdirs();
        folder.createNewFile();
    }

    @BeforeEach
    void beforeEach() {
        repository.clear();
        settingRepository.clear();
    }

    @AfterAll
    void afterAll() {
        File file = new File(testFile);
        file.delete();
        file.getParentFile().delete();
    }

    @Test()
    void it_should_be_able_to_save_games() throws Exception {
        settingRepository.save(new Setting(Path.of("test-folder"), Path.of("test-folder2")));
        sut.execute();
        Game result = repository.listAll().get(0);
        assertEquals("game", result.getName());
        assertEquals(Optional.empty(), result.getIconPath());
        assertEquals(1, repository.listAll().size());
    }

    @Test
    void it_should_not_save_games_given_folder_do_not_exists() {
        settingRepository.save(new Setting(Path.of("invalid-folder"), Path.of("test-folder2")));
        assertThrows(FileNotFoundException.class, sut::execute);
    }

    @Test
    void it_should_not_save_games_given_that_there_is_no_settings() {
        assertThrows(SettingNotFoundException.class, sut::execute);
    }

    @Test
    void it_should_not_duplicate_games() throws FileNotFoundException {
        settingRepository.save(new Setting(Path.of("test-folder"), Path.of("test-folder2")));
        sut.execute();
        sut.execute();
        assertEquals(1, repository.listAll().size());
    }
}
