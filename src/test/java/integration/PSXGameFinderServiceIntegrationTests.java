package integration;

import org.junit.jupiter.api.Test;
import org.retrolauncher.backend.app.games.infrastructure.services.PSXGameFinderService;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class PSXGameFinderServiceIntegrationTests {
    private final PSXGameFinderService sut = new PSXGameFinderService();

    @Test
    void it_should_be_able_to_find_psx_games() {
        final var file = new File("src/test/java/fixtures/files");
        final var result = sut.getFilesFrom(file);
        assertEquals(1, result.size());
        assertEquals("psx.cue", result.get(0).getName());
    }
}
