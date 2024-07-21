package org.retrolauncher.backend.app.platforms.domain.entities;

import lombok.Getter;
import org.retrolauncher.backend.app._shared.domain.entities.Entity;

import java.util.List;
import java.util.UUID;

@Getter
public class Platform extends Entity {
    private final String name;
    private final String corePath;

    public Platform(String name, String corePath) {
        super(UUID.randomUUID());

        this.name = name;
        this.corePath = corePath;
    }

    public Platform(UUID id, String name, String corePath) {
        super(id);

        this.name = name;
        this.corePath = corePath;
    }
}
