package org.retrolauncher.backend.app.games.infrastructure.database.jackson.mappers;

import org.retrolauncher.backend.app.games.domain.entities.Game;
import org.retrolauncher.backend.app.games.infrastructure.database.jackson.models.GameModel;
import org.retrolauncher.backend.app.platforms.domain.entities.Platform;

import java.nio.file.Path;

public class JacksonGameMapper {
    private JacksonGameMapper() {
    }

    public static GameModel fromDomain(Game entity) {
        GameModel model = new GameModel();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setPath(entity.getPath().toString());
        entity.getIconPath().ifPresent((path) -> model.setIconPath(path.toAbsolutePath().toString()));
        model.setPlatformId(entity.getPlatform().getId().toString());
        return model;
    }

    public static Game toDomain(GameModel model, Platform platform) {
        return new Game(
                model.getId(),
                model.getName(),
                Path.of(model.getPath()),
                model.getIconPath() != null ? Path.of(model.getIconPath()) : null,
                platform
        );
    }
}
