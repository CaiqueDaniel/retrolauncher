package org.retrolauncher.backend.app.platforms.infrastructure.database.hibernate.mappers;

import org.retrolauncher.backend.app.platforms.domain.entities.Platform;
import org.retrolauncher.backend.app.platforms.infrastructure.database.hibernate.models.PlatformModel;

public class HibernatePlatformMapper {
    private HibernatePlatformMapper() {
    }

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
