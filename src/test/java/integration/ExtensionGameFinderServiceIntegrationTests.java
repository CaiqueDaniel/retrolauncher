package integration;

import org.junit.jupiter.api.Test;
import org.retrolauncher.backend.app.games.infrastructure.services.ExtensionGameFinderService;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExtensionGameFinderServiceIntegrationTests {
    private final StubGameFinderService sut = new StubGameFinderService();

    @Test
    void it_should_be_able_to_find_games_by_extension() {
        final var file = new File("src/test/java/fixtures/files");
        final var result = sut.getFilesFrom(file);
        assertEquals(1, result.size());
        assertEquals("psx.cue", result.get(0).getName());
    }
}

class StubGameFinderService extends ExtensionGameFinderService {
    public StubGameFinderService() {
        super(new String[]{"cue"});
    }
}
