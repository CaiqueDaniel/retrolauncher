package integration;

import org.junit.jupiter.api.*;
import org.retrolauncher.app.games.application.dtos.GameInfoOutputDto;
import org.retrolauncher.app.games.application.usecases.ListGamesUseCase;
import org.retrolauncher.app.games.domain.entities.Game;
import org.retrolauncher.app.games.domain.repositories.GameRepository;
import org.retrolauncher.app.games.infrastructure.database.jackson.repositories.MemoryGameRepository;
import org.retrolauncher.app.platforms.domain.entities.Platform;
import org.retrolauncher.app.platforms.domain.repositories.PlatformRepository;
import org.retrolauncher.app.platforms.infrastructure.database.jackson.repositories.MemoryPlatformRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ListGamesUseCaseIntegrationTests {
    private final PlatformRepository platformRepository = new MemoryPlatformRepository();
    private final GameRepository repository = new MemoryGameRepository(this.platformRepository);
    private final ListGamesUseCase sut = new ListGamesUseCase(repository);
    private final Platform platform = new Platform("Test", "test", List.of("test"));

    @BeforeAll
    public void beforeAll() {
        platformRepository.save(platform);
    }

    @Test()
    public void it_should_be_able_to_list_games() {
        repository.save(new Game("test", "testpath", "icon.png", platform));
        List<GameInfoOutputDto> result = sut.execute();
        assertEquals(1, result.size());
    }
}
