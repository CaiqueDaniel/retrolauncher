package integration;

import org.junit.jupiter.api.*;
import org.retrolauncher.backend.app.platforms.application.exceptions.CoreFolderNotFoundException;
import org.retrolauncher.backend.app.platforms.application.services.PlatformsResourceConfigService;
import org.retrolauncher.backend.app.platforms.application.usecases.UpdatePlatformsListUseCase;
import org.retrolauncher.backend.app.platforms.domain.repositories.PlatformRepository;
import org.retrolauncher.backend.app.platforms.infrastructure.database.jackson.repositories.MemoryPlatformRepository;
import org.retrolauncher.backend.app.platforms.infrastructure.services.FilePlatformResourceConfigService;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UpdatePlatformsListUseCaseIntegrationTests {
    private final String testFile = "./test-folder/mesen.test";
    private final PlatformRepository repository = new MemoryPlatformRepository();
    private final PlatformsResourceConfigService resourceConfigService = new FilePlatformResourceConfigService();
    private final UpdatePlatformsListUseCase sut = new UpdatePlatformsListUseCase(repository, resourceConfigService);

    @BeforeAll
    void beforeAll() throws IOException {
        File folder = new File(testFile);
        folder.getParentFile().mkdirs();
        folder.createNewFile();
    }

    @BeforeEach
    void beforeEach() {
        repository.clear();
    }

    @AfterAll
    void afterAll() {
        File file = new File(testFile);
        file.delete();
        file.getParentFile().delete();
    }

    @Test()
    void it_should_be_able_to_save_cores_given_list_of_platforms() throws Exception {
        sut.execute("./test-folder");
        assertEquals(1, repository.listAll().size());
    }

    @Test
    void it_should_not_save_cores_given_folder_do_not_exists() {
        assertThrows(CoreFolderNotFoundException.class, () -> sut.execute("./invalid-folder"));
    }
}
