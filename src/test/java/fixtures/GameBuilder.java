package fixtures;

import org.retrolauncher.backend.app.games.domain.entities.Game;

import java.nio.file.Path;
import java.util.UUID;

public class GameBuilder {
    private UUID id = UUID.randomUUID();
    private UUID platformId = UUID.randomUUID();
    private String name = "Super Mario Bros.";
    private Path path = Path.of("");
    private Path iconPath = null;

    public static GameBuilder aGame() {
        return new GameBuilder();
    }

    public GameBuilder withPlatformId(UUID value) {
        this.platformId = value;
        return this;
    }

    public GameBuilder withName(String value) {
        this.name = value;
        return this;
    }

    public Game build() {
        return new Game(id, name, path, iconPath, platformId);
    }
}
