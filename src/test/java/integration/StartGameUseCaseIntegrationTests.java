package integration;

import org.junit.jupiter.api.*;
import org.retrolauncher.backend.app._shared.application.services.ProcessRunnerService;
import org.retrolauncher.backend.app.games.application.exceptions.GameNotFoundException;
import org.retrolauncher.backend.app.games.application.usecases.StartGameUseCase;
import org.retrolauncher.backend.app.games.domain.entities.Game;
import org.retrolauncher.backend.app.games.infrastructure.database.memory.MemoryGameRepository;
import org.retrolauncher.backend.app.platforms.domain.entities.Platform;
import org.retrolauncher.backend.app.platforms.domain.repositories.PlatformRepository;
import org.retrolauncher.backend.app.platforms.infrastructure.database.memory.MemoryPlatformRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StartGameUseCaseIntegrationTests {
    private final PlatformRepository platformRepository = new MemoryPlatformRepository();
    private final MemoryGameRepository repository = new MemoryGameRepository(this.platformRepository);
    private final ProcessRunnerService processRunnerService = mock(ProcessRunnerService.class);
    private final Platform platform = new Platform("Test", "test");
    private final StartGameUseCase sut = new StartGameUseCase(repository, this.processRunnerService);

    @BeforeAll
    public void beforeAll() throws IOException {
        when(processRunnerService.startGame(any()))
                .thenReturn(new ProcessBuilder().command("cmd", "/c", "echo", "\"test\"").start());
        platformRepository.save(platform);
    }

    @AfterEach
    void afterEach() {
        repository.clear();
    }

    @Test
    void it_should_start_game() {
        Game game = new Game("test", Path.of("testpath"), platform);
        repository.save(game);

        sut.execute(game.getId().toString());
        verify(processRunnerService, times(1)).startGame(any(Game.class));
    }

    @Test
    void it_should_not_start_game_when_game_does_not_exist() {
        assertThrows(GameNotFoundException.class, () -> sut.execute(UUID.randomUUID().toString()));
    }
}
