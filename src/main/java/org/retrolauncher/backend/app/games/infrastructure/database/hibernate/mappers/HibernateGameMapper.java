package org.retrolauncher.backend.app.games.infrastructure.database.hibernate.mappers;

import org.retrolauncher.backend.app.games.domain.entities.Game;
import org.retrolauncher.backend.app.games.infrastructure.database.hibernate.models.GameModel;

import java.nio.file.Path;
import java.util.UUID;

public class HibernateGameMapper {
    private HibernateGameMapper() {
    }

    public static GameModel fromDomain(Game entity) {
        GameModel model = new GameModel();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setPath(entity.getPath().toString());
        entity.getIconPath().ifPresent((path) -> model.setIconPath(path.toAbsolutePath().toString()));
        model.setPlatformId(entity.getPlatformId());
        return model;
    }

    public static Game toDomain(GameModel model) {
        return new Game(
                model.getId(),
                model.getName(),
                Path.of(model.getPath()),
                model.getIconPath() != null ? Path.of(model.getIconPath()) : null,
                model.getPlatformId()
        );
    }
}
