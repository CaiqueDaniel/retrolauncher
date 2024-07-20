package unit;

import org.junit.jupiter.api.Test;
import org.retrolauncher.backend.app.games.infrastructure.services.N64PlatformDetectorService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class N64PlatformDetectorServiceIntegrationTests {
    final N64PlatformDetectorService sut = new N64PlatformDetectorService();

    @Test
    void it_should_be_able_to_identify_a_n64_file() {
        List<File> files = new ArrayList<>(
                List.of(new File[]{new File("game.n64"), new File("game.z64")})
        );
        files.forEach((file) -> assertTrue(sut.isFromPlatform(file)));
    }

    @Test
    void it_should_not_identify_a_n64_file() {
        final File file = new File("src/test/java/fixtures/files/any.bin");
        assertFalse(sut.isFromPlatform(file));
    }
}
