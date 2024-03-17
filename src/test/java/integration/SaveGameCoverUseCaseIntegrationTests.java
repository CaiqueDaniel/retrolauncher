package integration;

import fixtures.StubCoverUploaderService;
import org.junit.jupiter.api.*;
import org.retrolauncher.backend.app.games.application.dtos.SaveGameCoverInputDto;
import org.retrolauncher.backend.app.games.application.usecases.SaveGameCoverUseCase;
import org.retrolauncher.backend.app.games.domain.entities.Game;
import org.retrolauncher.backend.app.games.domain.repositories.GameRepository;
import org.retrolauncher.backend.app.games.infrastructure.database.jackson.repositories.MemoryGameRepository;
import org.retrolauncher.backend.app.platforms.domain.entities.Platform;
import org.retrolauncher.backend.app.platforms.domain.repositories.PlatformRepository;
import org.retrolauncher.backend.app.platforms.infrastructure.database.jackson.repositories.MemoryPlatformRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SaveGameCoverUseCaseIntegrationTests {
    private final PlatformRepository platformRepository = new MemoryPlatformRepository();
    private final GameRepository repository = new MemoryGameRepository(this.platformRepository);
    private final Platform platform = new Platform("Test", "test", List.of("test"));
    private final SaveGameCoverUseCase sut = new SaveGameCoverUseCase(repository, new StubCoverUploaderService());

    @BeforeAll
    void beforeAll() throws IOException {
        platformRepository.save(platform);
        File folder = new File("test/game.png");
        folder.getParentFile().mkdirs();
        folder.createNewFile();
    }

    @AfterEach
    void afterEach() {
        repository.listAll().forEach((game) -> {
            new File(game.getIconPath().orElseThrow()).delete();
        });

        repository.clear();
    }

    @AfterAll
    void afterAll() {
        new File("test/game.png").delete();
    }

    @Test
    void it_should_be_able_to_save_a_cover_within_a_game() {
        Game game = new Game(UUID.randomUUID().toString(), "path", platform);
        String regex = "^test\\\\([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})\\.ico";
        File icon = new File("test/game.png");

        repository.save(game);
        sut.execute(new SaveGameCoverInputDto(game.getId().toString(), icon));

        Optional<String> result = repository.listAll().get(0).getIconPath();

        assertTrue(result.isPresent());
        assertTrue(result.get().matches(regex));
    }

    @Test
    void it_should_be_able_to_replace_a_existent_cover_within_a_game() {
        Game game = new Game(UUID.randomUUID().toString(), "path", platform);
        File icon = new File("test/game.png");

        game.uploadIcon("test/test.ico");
        repository.save(game);
        sut.execute(new SaveGameCoverInputDto(game.getId().toString(), icon));

        Optional<String> result = repository.listAll().get(0).getIconPath();

        assertTrue(result.isPresent());
        assertTrue(result.get().matches("test/test.ico"));
    }
}
