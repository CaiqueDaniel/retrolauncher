package unit;

import org.retrolauncher.backend.app.games.domain.entities.Game;

import org.retrolauncher.backend.app.games.infrastructure.database.jackson.mappers.JacksonGameMapper;
import org.retrolauncher.backend.app.games.infrastructure.database.jackson.models.GameModel;
import org.retrolauncher.backend.app.platforms.domain.entities.Platform;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JacksonGameMapperUnitTests {
    @Test
    void itShouldBeAbleToCreateAModel() {
        Platform platform = new Platform(UUID.randomUUID(), "Nintendo 64", "path", new ArrayList<>());
        Game game = new Game(UUID.randomUUID(), "Super Mario 64", "path", "icon", platform);
        GameModel result = JacksonGameMapper.fromDomain(game);

        assertEquals(result.getId(), game.getId());
        assertEquals(result.getName(), game.getName());
        assertEquals(result.getIconPath(), game.getIconPath().get());
        assertEquals(result.getPlatformId(), game.getPlatform().getId().toString());
    }

    @Test
    void itShouldBeAbleToCreateAEntity() {
        Platform platform = new Platform(UUID.randomUUID(), "Nintendo 64", "path", new ArrayList<>());
        Game game = new Game(UUID.randomUUID(), "Super Mario 64", "path", "icon", platform);
        GameModel model = JacksonGameMapper.fromDomain(game);
        Game result = JacksonGameMapper.toDomain(model, platform);

        assertEquals(result.getId(), game.getId());
        assertEquals(result.getName(), game.getName());
        assertEquals(result.getIconPath(), game.getIconPath());
        assertEquals(result.getPlatform().getId(), game.getPlatform().getId());
    }
}
