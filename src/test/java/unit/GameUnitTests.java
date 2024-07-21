package unit;

import org.junit.jupiter.api.Test;
import org.retrolauncher.backend.app.games.application.exceptions.GameValidationException;
import org.retrolauncher.backend.app.games.domain.entities.Game;
import org.retrolauncher.backend.app.platforms.domain.entities.Platform;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GameUnitTests {
    private final Platform platform = new Platform("Test", "");

    @Test
    void it_should_be_able_to_create() {
        Path gamePath = Path.of(System.getProperty("user.home"));
        Game game = new Game("test", gamePath, platform);
        assertEquals("test", game.getName());
        assertEquals(gamePath.toAbsolutePath(), game.getPath().toAbsolutePath());
        assertEquals(platform.getId(), game.getPlatform().getId());
        assertTrue(game.getIconPath().isEmpty());
    }

    @Test
    void it_should_not_be_able_to_set_name_using_invalid_data() {
        String[] invalidData = {"", null, " "};
        Game game = new Game("test", Path.of(""), platform);
        Arrays.stream(invalidData)
                .forEach((invalidName) -> assertThrows(GameValidationException.class, () -> game.setName(invalidName)));
    }
}
