package integration;

import fixtures.StubCoverFileSystemService;
import org.junit.jupiter.api.*;
import org.retrolauncher.backend.app.games.application.dtos.SaveGameCoverInputDto;
import org.retrolauncher.backend.app.games.application.usecases.SaveGameCoverUseCase;
import org.retrolauncher.backend.app.games.domain.entities.Game;
import org.retrolauncher.backend.app.games.infrastructure.database.memory.MemoryGameRepository;
import org.retrolauncher.backend.app.platforms.domain.entities.Platform;
import org.retrolauncher.backend.app.platforms.domain.repositories.PlatformRepository;
import org.retrolauncher.backend.app.platforms.infrastructure.database.memory.MemoryPlatformRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SaveGameCoverUseCaseIntegrationTests {
    private final PlatformRepository platformRepository = new MemoryPlatformRepository();
    private final MemoryGameRepository repository = new MemoryGameRepository(this.platformRepository);
    private final Platform platform = new Platform("Test", "test");
    private final SaveGameCoverUseCase sut = new SaveGameCoverUseCase(repository, new StubCoverFileSystemService());
    private final File cover = new File("test/game.png");

    @BeforeAll
    void beforeAll() {
        platformRepository.save(platform);
    }

    @BeforeEach
    void beforeEach() throws IOException {
        cover.getParentFile().mkdirs();
        cover.createNewFile();
    }

    @AfterEach
    void afterEach() {
        cover.delete();

        repository.listAll().forEach((game) -> {
            new File(game.getIconPath().get().toString()).delete();
        });

        repository.clear();
    }

    @Test
    void it_should_be_able_to_save_a_cover_within_a_game() {
        Game game = new Game(UUID.randomUUID().toString(), Path.of("path"), platform);
        File icon = new File("test/game.png");

        repository.save(game);
        sut.execute(new SaveGameCoverInputDto(game.getId().toString(), icon));

        Optional<Path> result = repository.listAll().get(0).getIconPath();

        assertTrue(result.isPresent());
    }

    @Test
    void it_should_be_able_to_replace_a_existent_cover_within_a_game() {
        Game game = new Game(UUID.randomUUID().toString(), Path.of("path"), platform);

        game.uploadIcon(Path.of("test/test.ico"));
        repository.save(game);
        sut.execute(new SaveGameCoverInputDto(game.getId().toString(), cover));

        Optional<Path> result = repository.listAll().get(0).getIconPath();

        assertTrue(result.isPresent());
        assertNotEquals(Path.of("test/test.ico").toAbsolutePath().toString(), result.get().toAbsolutePath().toString());
    }
}
