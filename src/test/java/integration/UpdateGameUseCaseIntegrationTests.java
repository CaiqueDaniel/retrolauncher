package integration;

import org.junit.jupiter.api.*;
import org.retrolauncher.backend.app.games.application.dtos.UpdateGameInputDto;
import org.retrolauncher.backend.app.games.application.exceptions.GameNotFoundException;
import org.retrolauncher.backend.app.games.application.usecases.UpdateGameUseCase;
import org.retrolauncher.backend.app.games.domain.entities.Game;
import org.retrolauncher.backend.app.games.infrastructure.database.memory.MemoryGameRepository;
import org.retrolauncher.backend.app.platforms.domain.entities.Platform;
import org.retrolauncher.backend.app.platforms.domain.repositories.PlatformRepository;
import org.retrolauncher.backend.app.platforms.infrastructure.database.memory.MemoryPlatformRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UpdateGameUseCaseIntegrationTests {
    private final PlatformRepository platformRepository = new MemoryPlatformRepository();
    private final MemoryGameRepository repository = new MemoryGameRepository(this.platformRepository);
    private final Platform platform = new Platform("Test", "test");
    private final UpdateGameUseCase sut = new UpdateGameUseCase(repository);

    @BeforeAll
    void beforeAll() throws IOException {
        platformRepository.save(platform);
    }

    @AfterEach
    void afterEach() {
        repository.clear();
    }

    @Test
    void it_should_be_able_to_update_game_data() {
        Game game = new Game(UUID.randomUUID().toString(), Path.of("path"), platform);
        repository.save(game);
        sut.execute(new UpdateGameInputDto(game.getId(), "Renamed"));
        String result = repository.listAll().get(0).getName();
        assertEquals("Renamed", result);
    }

    @Test
    void it_should_not_update_game_data_when_game_is_not_found() {
        Game game = new Game(UUID.randomUUID().toString(), Path.of("path"), platform);
        repository.save(game);
        assertThrows(
                GameNotFoundException.class,
                () -> sut.execute(new UpdateGameInputDto(UUID.randomUUID(), "Renamed"))
        );
        String result = repository.listAll().get(0).getName();
        assertEquals(game.getName(), result);
    }
}
