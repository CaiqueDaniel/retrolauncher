package integration;

import org.junit.jupiter.api.*;
import org.retrolauncher.backend.app.games.application.dtos.GameInfoOutputDto;
import org.retrolauncher.backend.app.games.application.usecases.ListGamesUseCase;
import org.retrolauncher.backend.app.games.domain.entities.Game;
import org.retrolauncher.backend.app.games.domain.repositories.GameRepository;
import org.retrolauncher.backend.app.games.infrastructure.database.jackson.repositories.MemoryGameRepository;
import org.retrolauncher.backend.app.platforms.domain.entities.Platform;
import org.retrolauncher.backend.app.platforms.domain.repositories.PlatformRepository;
import org.retrolauncher.backend.app.platforms.infrastructure.database.jackson.repositories.MemoryPlatformRepository;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ListGamesUseCaseIntegrationTests {
    private final PlatformRepository platformRepository = new MemoryPlatformRepository();
    private final GameRepository repository = new MemoryGameRepository(this.platformRepository);
    private final ListGamesUseCase sut = new ListGamesUseCase(repository);
    private final Platform platform = new Platform("Test", "test", List.of("test"));

    @BeforeAll
    void beforeAll() {
        platformRepository.save(platform);
    }

    @AfterEach
    void afterEach() {
        repository.clear();
    }

    @Test()
    void it_should_be_able_to_list_games() {
        repository.save(new Game("test", Path.of("testpath"), platform));
        List<GameInfoOutputDto> result = sut.execute();
        assertEquals(1, result.size());
        assertEquals("test", result.get(0).name());
        assertEquals(Optional.empty(), result.get(0).iconPath());
    }

    @Test()
    void it_should_be_able_to_list_games_with_icon() {
        Game game = new Game("test", Path.of("testpath"), platform);
        game.uploadIcon(Path.of("icon.png"));
        repository.save(game);
        List<GameInfoOutputDto> result = sut.execute();
        assertEquals(1, result.size());
        assertEquals("test", result.get(0).name());
        assertEquals(Path.of("icon.png").toAbsolutePath(), result.get(0).iconPath().get().toAbsolutePath());
    }
}
