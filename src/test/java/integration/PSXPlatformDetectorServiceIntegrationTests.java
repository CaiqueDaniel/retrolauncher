package integration;

import org.junit.jupiter.api.Test;
import org.retrolauncher.backend.app.games.infrastructure.services.PSXPlatformDetectorService;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class PSXPlatformDetectorServiceIntegrationTests {
    final PSXPlatformDetectorService sut = new PSXPlatformDetectorService();

    @Test
    void it_should_be_able_to_identify_a_ps1_file() {
        final File file = new File("src/test/java/fixtures/files/psx.bin");
        assertTrue(sut.isFromPlatform(file));
    }

    @Test
    void it_should_not_identify_a_ps1_file() {
        final File file = new File("src/test/java/fixtures/files/any.bin");
        assertFalse(sut.isFromPlatform(file));
    }
}
