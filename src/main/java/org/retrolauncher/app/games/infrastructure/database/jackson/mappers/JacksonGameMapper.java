package org.retrolauncher.app.games.infrastructure.database.jackson.mappers;

import org.retrolauncher.app.games.domain.entities.Game;
import org.retrolauncher.app.games.infrastructure.database.jackson.models.GameModel;
import org.retrolauncher.app.platforms.domain.entities.Platform;

public class JacksonGameMapper {
    public static GameModel fromDomain(Game entity) {
        GameModel model = new GameModel();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setPath(entity.getPath());
        model.setIconPath(entity.getIconPath());
        model.setPlatformId(entity.getPlatform().getId().toString());
        return model;
    }

    public static Game toDomain(GameModel model, Platform platform) {
        return new Game(
                model.getId(),
                model.getName(),
                model.getPath(),
                model.getIconPath(),
                platform
        );
    }
}
