package integration;

import fixtures.TestHibernateDriver;
import org.junit.jupiter.api.*;
import org.retrolauncher.backend.app.games.application.dtos.GameSearchResult;
import org.retrolauncher.backend.app.games.application.usecases.ListGamesUseCase;
import org.retrolauncher.backend.app.games.domain.entities.Game;
import org.retrolauncher.backend.app.games.infrastructure.database.hibernate.repositories.HibernateGameQueryRepository;
import org.retrolauncher.backend.app.games.infrastructure.database.hibernate.repositories.HibernateGameRepository;
import org.retrolauncher.backend.app.platforms.domain.entities.Platform;
import org.retrolauncher.backend.app.platforms.infrastructure.database.hibernate.repositories.HibernatePlatformRepository;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ListGamesUseCaseIntegrationTests {
    private final TestHibernateDriver driver = new TestHibernateDriver();
    private final HibernatePlatformRepository platformRepository = new HibernatePlatformRepository(driver.getSessionFactory());
    private final HibernateGameRepository repository = new HibernateGameRepository(driver.getSessionFactory());
    private final ListGamesUseCase sut = new ListGamesUseCase(new HibernateGameQueryRepository(driver.getSessionFactory()));
    private final Platform platform = new Platform("Test", "test");

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
        repository.save(new Game("test", Path.of("testpath"), platform.getId()));
        List<GameSearchResult> result = sut.execute();
        assertEquals(1, result.size());
        assertEquals("test", result.get(0).name());
        assertEquals(platform.getName(), result.get(0).platformName());
        assertEquals(Optional.empty(), result.get(0).iconPath());
    }
}
