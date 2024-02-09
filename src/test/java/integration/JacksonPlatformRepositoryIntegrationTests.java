package integration;

import org.junit.jupiter.api.*;
import org.retrolauncher.app.platforms.domain.entities.Platform;
import org.retrolauncher.app.platforms.infrastructure.database.jackson.models.PlatformModel;
import org.retrolauncher.app.platforms.infrastructure.database.jackson.repositories.JacksonPlatformRepository;
import org.retrolauncher.database.FileDatabaseDriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JacksonPlatformRepositoryIntegrationTests {
    private JacksonPlatformRepository sut;
    private Platform platform;

    @BeforeAll
    public void setup() {
        sut = new JacksonPlatformRepository(new FileDatabaseDriver<>() {
            private Map<String, PlatformModel> storedData = new HashMap<>();

            @Override
            public void write(Map<String, PlatformModel> content, String path) throws IOException {
                this.storedData = content;
            }

            @Override
            public Map<String, PlatformModel> read(String path) throws IOException {
                return this.storedData;
            }

            @Override
            public void clear(String path) throws IOException {
                this.storedData.clear();
            }
        });
    }

    @BeforeEach
    public void beforeEach() {
        platform = new Platform("Nintendo 64", "path", new ArrayList<>());
    }

    @AfterEach
    public void destroy() {
        sut.clear();
    }

    @Test
    public void itShouldBeAbleToSave() {
        sut.save(platform);
        assertEquals(1, sut.listAll().size());
    }

    @Test
    public void itShouldBeAbleToClear() {
        sut.save(platform);
        assertEquals(1, sut.listAll().size());
        sut.clear();
        assertEquals(0, sut.listAll().size());
    }

    @Test
    public void itShouldBeAbleToFindAPlatformById() {
        sut.save(platform);
        assertTrue(sut.findById(platform.getId()).isPresent());
        assertEquals(platform.getId(), sut.findById(platform.getId()).get().getId());
    }
}
