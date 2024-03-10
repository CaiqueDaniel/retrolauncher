package integration;

import org.junit.jupiter.api.*;
import org.retrolauncher.backend.app._shared.infrastructure.services.CoverUploaderService;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SaveGameCoverUseCaseIntegrationTests {
    private final PlatformRepository platformRepository = new MemoryPlatformRepository();
    private final GameRepository repository = new MemoryGameRepository(this.platformRepository);
    private final Platform platform = new Platform("Test", "test", List.of("test"));
    private final Game game = new Game(UUID.randomUUID().toString(), "path", "path", platform);
    private final SaveGameCoverUseCase sut = new SaveGameCoverUseCase(repository, new CoverUploaderService());

    @BeforeAll
    void beforeAll() throws IOException {
        platformRepository.save(platform);
        File folder = new File("test-folder/game.png");
        folder.getParentFile().mkdirs();
        folder.createNewFile();
    }

    @BeforeEach
    void beforeEach() {
        repository.listAll().forEach((game) -> {
            new File(game.getIconPath()).delete();
        });
        repository.save(game);
    }

    @AfterEach
    void afterEach() {
        repository.clear();
    }

    @AfterAll
    void afterAll() {
        new File("test-folder/game.png").delete();
    }

    @Test
    void it_should_be_able_to_save_a_cover_within_a_game() {
        String baseDir = new StringBuilder(System.getProperty("user.home"))
                .append("/retro-launcher")
                .append("/covers").toString();
        File icon = new File("test-folder/game.png");
        sut.execute(new SaveGameCoverInputDto(game.getId().toString(), icon));
        assertNotEquals("", repository.listAll().get(0).getIconPath());
    }
}
