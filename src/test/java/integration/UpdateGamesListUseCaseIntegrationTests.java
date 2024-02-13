package integration;

import org.junit.jupiter.api.*;
import org.retrolauncher.app.games.domain.repositories.GameRepository;
import org.retrolauncher.app.games.infrastructure.database.jackson.repositories.MemoryGameRepository;
import org.retrolauncher.app.games.application.usecases.UpdateGamesListUseCase;
import org.retrolauncher.app.platforms.domain.entities.Platform;
import org.retrolauncher.app.platforms.domain.repositories.PlatformRepository;
import org.retrolauncher.app.platforms.infrastructure.database.jackson.repositories.MemoryPlatformRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UpdateGamesListUseCaseIntegrationTests {
    private final String testFile = "./test-folder/game.test";
    private final PlatformRepository platformRepository = new MemoryPlatformRepository();
    private final GameRepository repository = new MemoryGameRepository(this.platformRepository);
    private final UpdateGamesListUseCase sut = new UpdateGamesListUseCase(repository, this.platformRepository);

    @BeforeAll
    public void beforeAll() throws IOException {
        platformRepository.save(new Platform("Test", "test", List.of("test")));
        File folder = new File(testFile);
        folder.getParentFile().mkdirs();
        folder.createNewFile();
    }

    @BeforeEach
    public void beforeEach() {
        repository.clear();
    }

    @AfterAll
    public void afterAll() {
        File file = new File(testFile);
        file.delete();
        file.getParentFile().delete();
    }

    @Test()
    public void it_should_be_able_to_save_games() throws Exception {
        sut.execute("./test-folder");
        assertEquals(1, repository.listAll().size());
    }

    @Test
    public void it_should_not_save_games_given_folder_do_not_exists() {
        assertThrows(FileNotFoundException.class, () -> sut.execute("./invalid-folder"));
    }
}
