package integration;

import org.junit.jupiter.api.*;
import org.retrolauncher.app.games.domain.entities.Game;
import org.retrolauncher.app.games.infrastructure.database.jackson.repositories.JacksonGameRepository;
import org.retrolauncher.app.platforms.domain.entities.Platform;
import org.retrolauncher.app.platforms.domain.repositories.PlatformRepository;
import org.retrolauncher.app.platforms.infrastructure.database.jackson.repositories.JacksonPlatformRepository;
import org.retrolauncher.database.MemoryFileDatabaseDriver;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JacksonGameRepositoryIntegrationTests {
    private JacksonGameRepository sut;
    Platform platform;
    private Game game;

    @BeforeAll
    public void beforeAll() {
        PlatformRepository platformRepository = new JacksonPlatformRepository(new MemoryFileDatabaseDriver<>());
        sut = new JacksonGameRepository(new MemoryFileDatabaseDriver<>(), platformRepository);
        platform = new Platform("Nintendo 64", "path", new ArrayList<>());
        platformRepository.save(platform);
    }

    @BeforeEach
    public void beforeEach() {
        game = new Game("Teste", "teste.test", "icon.png", platform);
    }

    @AfterEach
    public void afterEach() {
        sut.clear();
    }

    @Test
    public void itShouldBeAbleToSave() {
        sut.save(game);
        assertEquals(1, sut.listAll().size());
    }

    @Test
    public void itShouldBeAbleToClear() {
        sut.save(game);
        assertEquals(1, sut.listAll().size());
        sut.clear();
        assertEquals(0, sut.listAll().size());
    }

    @Test
    public void itShouldBeAbleToFindAGameById() {
        sut.save(game);
        assertTrue(sut.findById(game.getId()).isPresent());
        assertEquals(game.getId(), sut.findById(game.getId()).get().getId());
    }

    @Test
    public void itShouldConfirmThatAGameWasAddedGivenThePath() {
        sut.save(game);
        boolean result = sut.existsByPath(game.getPath());
        assertTrue(result);
    }

    @Test
    public void itShouldNotConfirmThatAGameWasAddedGivenAPathThatNotExists() {
        sut.save(game);
        boolean result = sut.existsByPath("invalid-path");
        assertFalse(result);
    }
}
