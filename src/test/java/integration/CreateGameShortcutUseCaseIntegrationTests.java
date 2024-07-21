package integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.retrolauncher.backend.app._shared.application.dtos.Shortcut;
import org.retrolauncher.backend.app._shared.application.services.ShortcutService;
import org.retrolauncher.backend.app.games.application.exceptions.GameNotFoundException;
import org.retrolauncher.backend.app.games.application.usecases.CreateGameShortcutUseCase;
import org.retrolauncher.backend.app.games.domain.entities.Game;
import org.retrolauncher.backend.app.games.infrastructure.database.memory.MemoryGameRepository;
import org.retrolauncher.backend.app.platforms.domain.entities.Platform;
import org.retrolauncher.backend.app.platforms.domain.repositories.PlatformRepository;
import org.retrolauncher.backend.app.platforms.infrastructure.database.memory.MemoryPlatformRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateGameShortcutUseCaseIntegrationTests {
    private final PlatformRepository platformRepository = new MemoryPlatformRepository();
    private final MemoryGameRepository repository = new MemoryGameRepository(this.platformRepository);
    private final ShortcutService shortcutService = mock(ShortcutService.class);
    private final Platform platform = new Platform("Test", "test");
    private final CreateGameShortcutUseCase sut = new CreateGameShortcutUseCase(repository, shortcutService);

    @BeforeAll
    public void beforeAll() {
        platformRepository.save(platform);
    }

    @AfterEach
    void afterEach() {
        repository.clear();
    }

    @Test
    void it_should_create_a_shortcut() throws IOException {
        Game game = new Game("test", Path.of("testpath"), platform);
        repository.save(game);

        sut.execute(game.getId().toString());
        verify(shortcutService, times(1)).create(any(Shortcut.class));
    }

    @Test
    void it_should_not_create_a_shortcut_when_game_does_not_exist() {
        assertThrows(GameNotFoundException.class, () -> sut.execute(UUID.randomUUID().toString()));
    }
}
