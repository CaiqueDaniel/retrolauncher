package org.retrolauncher.backend.app._shared.domain.entities;

import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class Entity {
    protected final UUID id;

    protected Entity(UUID id) {
        this.id = id;
    }
}
