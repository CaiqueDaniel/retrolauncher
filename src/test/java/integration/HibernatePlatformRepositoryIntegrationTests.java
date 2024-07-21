package integration;

import fixtures.TestHibernateDriver;
import org.junit.jupiter.api.*;
import org.retrolauncher.backend.app.platforms.domain.entities.Platform;
import org.retrolauncher.backend.app.platforms.infrastructure.database.hibernate.repositories.HibernatePlatformRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HibernatePlatformRepositoryIntegrationTests {
    private HibernatePlatformRepository sut;
    private Platform platform;

    @BeforeAll
    void setup() {
        sut = new HibernatePlatformRepository(new TestHibernateDriver().getSessionFactory());
    }

    @BeforeEach
    void beforeEach() {
        sut.clear();
        platform = new Platform("Nintendo 64", "path");
    }

    @Test
    void it_should_be_able_to_save() {
        sut.save(platform);
        assertEquals(1, sut.listAll().size());
    }

    @Test
    void it_should_be_able_to_find_by_id() {
        sut.save(platform);
        assertTrue(sut.findById(platform.getId()).isPresent());
        assertEquals(platform.getId(), sut.findById(platform.getId()).get().getId());
    }

    @Test
    void it_should_be_able_to_find_by_core_path() {
        sut.save(platform);
        assertTrue(sut.existsByCore(platform.getCorePath()));
    }
}
