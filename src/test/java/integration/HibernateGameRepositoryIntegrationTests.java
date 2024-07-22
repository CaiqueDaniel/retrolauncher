package integration;

import fixtures.TestHibernateDriver;
import org.junit.jupiter.api.*;
import org.retrolauncher.backend.app.games.domain.entities.Game;
import org.retrolauncher.backend.app.games.infrastructure.database.hibernate.repositories.HibernateGameRepository;
import org.retrolauncher.backend.app.platforms.domain.entities.Platform;
import org.retrolauncher.backend.app.platforms.infrastructure.database.hibernate.repositories.HibernatePlatformRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HibernateGameRepositoryIntegrationTests {
    private HibernateGameRepository sut;
    Platform platform;
    private Game game;

    @BeforeAll
    void beforeAll() throws IOException {
        var driver = new TestHibernateDriver();
        var platformRepository = new HibernatePlatformRepository(driver.getSessionFactory());
        sut = new HibernateGameRepository(driver.getSessionFactory());
        platform = new Platform("Nintendo 64", "path");
        platformRepository.save(platform);
    }

    @BeforeEach
    void beforeEach() {
        game = new Game("Teste", Path.of("teste.test"), platform.getId());
    }

    @AfterEach
    void afterEach() {
        sut.clear();
    }

    @Test
    void it_should_be_able_to_save() {
        sut.save(game);
        assertEquals(1, sut.listAll().size());
    }

    @Test
    void it_should_be_able_to_update() {
        sut.save(game);
        game.setName("test 2");
        sut.save(game);
        assertEquals("test 2", sut.findById(game.getId()).get().getName());
    }

    @Test
    void it_should_be_able_to_find_a_game_by_id() {
        sut.save(game);
        assertTrue(sut.findById(game.getId()).isPresent());
        assertEquals(game.getId(), sut.findById(game.getId()).get().getId());
    }

    @Test
    void it_should_confirm_that_a_game_was_added_given_the_path() {
        sut.save(game);
        boolean result = sut.existsByPath(game.getPath().toString());
        assertTrue(result);
    }

    @Test
    void it_should_not_confirm_that_a_game_was_added_given_a_path_that_not_exists() {
        sut.save(game);
        boolean result = sut.existsByPath("invalid-path");
        assertFalse(result);
    }

    @Test
    void it_should_be_able_to_delete_a_game() {
        sut.save(game);
        assertTrue(sut.findById(game.getId()).isPresent());
        sut.delete(game);
        assertFalse(sut.findById(game.getId()).isPresent());
    }

    @Test
    void it_should_be_able_to_find_a_game_by_name_and_platform_id() {
        sut.save(game);
        var result = sut.findOneByNameAndPlatformId(game.getName(), game.getPlatformId());
        assertTrue(result.isPresent());
    }

    @Test
    void it_should_be_able_to_find_games_given_ids_are_not_within_the_provided_set() {
        sut.save(game);
        var result = sut.findAllByIdsNotIn(new HashSet<>(List.of(new String[]{game.getId().toString()})));
        assertTrue(result.isEmpty());
    }
}
