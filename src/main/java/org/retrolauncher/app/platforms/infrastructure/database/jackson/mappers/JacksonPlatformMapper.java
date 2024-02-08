package org.retrolauncher.app.platforms.infrastructure.database.jackson.mappers;

import org.retrolauncher.app.platforms.domain.entities.Platform;
import org.retrolauncher.app.platforms.infrastructure.database.jackson.models.PlatformModel;

public class JacksonPlatformMapper {
    public static PlatformModel fromDomain(Platform entity) {
        PlatformModel model = new PlatformModel();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setCorePath(entity.getCorePath());
        model.setExtensions(entity.getExtensions());
        return model;
    }

    public static Platform toDomain(PlatformModel model) {
        return new Platform(
                model.getId(),
                model.getName(),
                model.getCorePath(),
                model.getExtensions()
        );
    }
}
